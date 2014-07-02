using System;
using System.Runtime.Serialization;

namespace Tasky.Core {
	/// <summary>
	/// Task business object
	/// </summary>
	/// 
	[DataContract]
	public class Task {
		public Task ()
		{
		}
		[DataMember(Name="_id")]
        public string ID { get; set; }
		[DataMember]
		public string Name { get; set; }
		[DataMember]
		public string Notes { get; set; }
		[DataMember]
		public bool Done { get; set; }	// TODO: add this field to the user-interface
	}
}