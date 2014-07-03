using System;
using System.Collections.Generic;
using System.IO;
using System.Xml;
using System.Xml.Serialization;
using System.Linq;

namespace Tasky.Core {
	/// <summary>
	/// The repository is responsible for providing an abstraction to actual data storage mechanism
	/// whether it be SQLite, XML or some other method
	/// </summary>
	public class TaskRepositoryXML {
		static string storeLocation;	
		static List<Task> tasks;

		static TaskRepositoryXML ()
		{
			// set the db location
			storeLocation = DatabaseFilePath;
			tasks = new List<Task> ();

			// deserialize XML from file at dbLocation
			ReadXml ();
		}

		static void ReadXml ()
		{
			if (File.Exists (storeLocation)) {
				var serializer = new XmlSerializer (typeof(List<Task>));
				using (var stream = new FileStream (storeLocation, FileMode.Open)) {
					tasks = (List<Task>)serializer.Deserialize (stream);
				}
			}
		}
		static void WriteXml ()
		{
			var serializer = new XmlSerializer (typeof(List<Task>));
			using (var writer = new StreamWriter (storeLocation)) {
				serializer.Serialize (writer, tasks);
			}
		}

		public static string DatabaseFilePath {
			get { 
				var storeFilename = "TaskDB.xml";
				#if SILVERLIGHT
				// Windows Phone expects a local path, not absolute
				var path = sqliteFilename;
				#else

				#if __ANDROID__
				// Just use whatever directory SpecialFolder.Personal returns
				string libraryPath = Environment.GetFolderPath(Environment.SpecialFolder.Personal); ;
				#else
				// we need to put in /Library/ on iOS5.1 to meet Apple's iCloud terms
				// (they don't want non-user-generated data in Documents)
				string documentsPath = Environment.GetFolderPath (Environment.SpecialFolder.Personal); // Documents folder
				string libraryPath = Path.Combine (documentsPath, "..", "Library"); // Library folder
				#endif
				var path = Path.Combine (libraryPath, storeFilename);
				#endif		
				return path;	
			}
		}

		public static Task GetTask(int id)
		{
			for (var t = 0; t< tasks.Count; t++) {
				if (tasks[t].ID == id)
					return tasks[t];
			}
			return new Task() {ID=id};
		}

		public static IEnumerable<Task> GetTasks ()
		{
			return tasks;
		}

		/// <summary>
		/// Insert or update a task
		/// </summary>
		public static int SaveTask (Task item)
		{ 
			var max = 0;
			if (tasks.Count > 0) 
				max = tasks.Max(x => x.ID);

			if (item.ID == 0) {
				item.ID = ++max;
				tasks.Add (item);
			} else {
				var i = tasks.Select (x => x.ID == item.ID?x:null).First();//tasks.Find (x => x.ID == item.ID);
				i = item; // replaces item in collection with updated value
			}

			WriteXml ();
			return max;
		}

		public static int DeleteTask(int id)
		{
			for (var t = 0; t< tasks.Count; t++) {
				if (tasks[t].ID == id){
					tasks.RemoveAt (t);
					WriteXml ();
					return 1;
				}
			}

			return -1;
		}
	}
}