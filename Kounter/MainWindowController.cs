
using System;
using System.Collections.Generic;
using System.Linq;
using MonoMac.Foundation;
using MonoMac.AppKit;

namespace Kounter
{
	public partial class MainWindowController : MonoMac.AppKit.NSWindowController
	{
		#region Constructors

		// Called when created from unmanaged code
		public MainWindowController (IntPtr handle) : base (handle)
		{
			Initialize ();
		}
		
		// Called when created directly from a XIB file
		[Export ("initWithCoder:")]
		public MainWindowController (NSCoder coder) : base (coder)
		{
			Initialize ();
		}
		
		// Call to load from the XIB/NIB file
		public MainWindowController () : base ("MainWindow")
		{
			Initialize ();
		}
		
		// Shared initialization code
		void Initialize ()
		{

		
		//	Console.WriteLine (KCClient.GetCount ().ToString());
			Console.WriteLine ("en main window");

		}

		public override void WindowDidLoad ()
		{
			loadCounter ();
			NSTimer timer = NSTimer.CreateRepeatingScheduledTimer (TimeSpan.FromSeconds (5.0), delegate {
				loadCounter();
			});
				
		}

		void loadCounter() {
			var value = KCClient.GetCount ().ToString ();
			Console.WriteLine(value);
			this.labelCount.StringValue = value;
		}
	
		#endregion

		//strongly typed window accessor
		public new MainWindow Window {
			get {
				return (MainWindow)base.Window;
			}
		}
	}
}

