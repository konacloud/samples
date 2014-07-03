using System;
using System.Runtime.Serialization;

namespace Tasky.Core
{
	[DataContract]
	public class KCRequest<T>
	{
		public KCRequest ()
		{
		}

	//	[DataMember(Name = "kona", IsRequired = true)]
	

		[DataMember(Name = "success", IsRequired = true)]
		public bool success {
			get;
			set;
		}

		[DataMember(Name = "errorMsg", IsRequired = true)]
		public string errorMsg {
			get;
			set;
		}

		[DataMember(Name = "executionTime", IsRequired = true)]
		public long executionTime {
			get;
			set;
		}

		[DataMember(Name = "cpuUsage", IsRequired = true)]
		public long cpuUsage {
			get;
			set;
		}

		[DataMember(Name = "data", IsRequired = true)]
		public T data {
			get;
			set;
		}
	}

}

