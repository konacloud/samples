
using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Runtime.Serialization.Json;

namespace Tasky.Core {

	public class KCTaskRepository {

		const string auth_token = "4c92d618-e8f5-429a-8d2b-980ddb49211f";
		const string url = "http://app.konacloud.io/api/demo/Tasky/mr_Task";
	
		protected static KCTaskRepository me;		

		static KCTaskRepository ()
		{
			me = new KCTaskRepository();
		}

		protected KCTaskRepository ()
		{
			// set the db location

			// instantiate the database	
		//	db = new TaskDatabase(dbLocation);
		}


		public static Task GetTask(int id)
		{
			var request = WebRequest.Create(string.Format("{0}?id={1}", url, id));
			request.ContentType = "application/json";
			request.Method = "GET";
			request.Headers.Add ("X-AUTH-TOKEN", auth_token);
		
			using (HttpWebResponse response = request.GetResponse() as HttpWebResponse)
			{
				if (response.StatusCode != HttpStatusCode.OK) {
					Console.Out.WriteLine ("Error fetching data. Server returned status code: {0}", response.StatusCode);
				}

				var stream = response.GetResponseStream ();
				DataContractJsonSerializer ser = new DataContractJsonSerializer(typeof(KCRequest<List<Task>>));
				KCRequest<Task> task = (KCRequest<Task>)ser.ReadObject(stream);
				return task.data;
			}

		}


		public static IEnumerable<Task> GetTasks ()
		{

			var request = WebRequest.Create(url);
			request.ContentType = "application/json";
			request.Method = "GET";
			request.Headers.Add ("X-AUTH-TOKEN", auth_token);

			using (HttpWebResponse response = request.GetResponse() as HttpWebResponse)
			{
				if (response.StatusCode != HttpStatusCode.OK) {
					Console.Out.WriteLine ("Error fetching data. Server returned status code: {0}", response.StatusCode);
				}

				var stream = response.GetResponseStream ();
				DataContractJsonSerializer ser = new DataContractJsonSerializer(typeof(KCRequest<List<Task>>));
				KCRequest<List<Task>> tasks = (KCRequest<List<Task>>)ser.ReadObject(stream);
				return tasks.data;
			}
		}

		public static string SaveTask (Task item)
		{
			var request = WebRequest.Create(url);
			request.ContentType = "application/json";
			request.Method = "POST";
			request.Headers.Add ("X-AUTH-TOKEN", auth_token);

			MemoryStream stream1 = new MemoryStream();
			DataContractJsonSerializer ser = new DataContractJsonSerializer(typeof(Task));
			ser.WriteObject(stream1, item);

			stream1.Position = 0;
			var strObject = new StreamReader (stream1).ReadToEnd ();

			using (var writer = new StreamWriter(request.GetRequestStream()))
			{
				writer.Write(strObject);
			}

			using (HttpWebResponse response = request.GetResponse () as HttpWebResponse) {
				var stream = response.GetResponseStream ();
				//KCRequest<Task> konaresp = (KCRequest<Task>)ser.ReadObject(stream);
				//return konaresp.data.ID;
			}

			return String.Empty;
		}

		public static int DeleteTask(string id)
		{
			var request = WebRequest.Create(string.Format("{0}?id={1}", url, id));
			request.ContentType = "application/json";
			request.Method = "DELETE";
			request.Headers.Add ("X-AUTH-TOKEN", auth_token);

			HttpWebResponse response = request.GetResponse () as HttpWebResponse;
	
			if (response.StatusCode != HttpStatusCode.OK) {
				Console.Out.WriteLine ("Error fetching data. Server returned status code: {0}", response.StatusCode);
			}

			var stream = response.GetResponseStream ();
			//DataContractJsonSerializer ser = new DataContractJsonSerializer(typeof(KCRequest<List<Task>>));
			//KCRequest<string> konaresp = (KCRequest<string>)ser.ReadObject(stream);
		

			return 1;
		}
	}
}

