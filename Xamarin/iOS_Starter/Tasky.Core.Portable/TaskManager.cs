using System;
using System.Collections.Generic;

namespace Tasky.Core {
	/// <summary>
	/// Manager classes are an abstraction on the data access layers
	/// </summary>
	public static class TaskManager {
		static TaskManager ()
		{

		}
		
		public static Task GetTask(int id)
		{
			return KCTaskRepository.GetTask(id);
		}
		
		public static IList<Task> GetTasks ()
		{
			return new List<Task>(KCTaskRepository.GetTasks());
		}
		
		public static string SaveTask (Task item)
		{
			return KCTaskRepository.SaveTask(item);
		}
		
		public static int DeleteTask(string id)
		{
			return KCTaskRepository.DeleteTask(id);
		}
	}
}