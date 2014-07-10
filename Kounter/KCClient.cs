using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Runtime.Serialization.Json;
using System.Runtime.Serialization;

namespace Kounter {

	[DataContract]
	class KonaResponse {

		[DataMember(Name = "req_count", IsRequired = true)]
		public int ReqCount {
			get;
			set;
		}
	}

	public static class KCClient {

		const string url = "http://app.konacloud.io/external/api/count";
				
		public static int GetCount()
		{
			var request = WebRequest.Create(url);
			request.ContentType = "application/json";
			request.Method = "GET";
		
			using (HttpWebResponse response = request.GetResponse() as HttpWebResponse)
			{
				if (response.StatusCode != HttpStatusCode.OK) {
					Console.Out.WriteLine ("Error fetching data. Server returned status code: {0}", response.StatusCode);
				}

				var stream = response.GetResponseStream ();

				DataContractJsonSerializer ser = new DataContractJsonSerializer(typeof(KonaResponse));
				KonaResponse resp = (KonaResponse)ser.ReadObject(stream);
				return resp.ReqCount;
			}
		}
	
	}
}

