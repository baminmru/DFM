using System;
using System.Data;
using System.IO;
using System.Xml.Serialization ;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters;  
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using dv21;
using dv21_list;
using dv21_load;

namespace dv21_util 
{

	public class MyUtils
	{

		[STAThread]
		static void Main() 
		{
			Application.Run(new Form2());
		}


		


		public static void SerializeObject(string filename, dv21.CardDefinition cd)
		{
			try 
			{  
				XmlSerializer serializer = 
					new XmlSerializer(typeof(dv21.CardDefinition));
		
				// Create an XmlSerializerNamespaces object.
				XmlSerializerNamespaces ns = 
					new XmlSerializerNamespaces();
				// Add two namespaces with prefixes.
				//ns.Add("inventory", "http://www.cpandl.com");
				//ns.Add("money", "http://www.cohowinery.com");

				// Create an XmlTextWriter using a FileStream.
				Stream fs = new FileStream(filename, FileMode.Create);
				System.Xml.XmlWriter writer = 
					new System.Xml.XmlTextWriter(fs, new System.Text.UTF8Encoding());
				// Serialize using the XmlTextWriter.
				serializer.Serialize(writer, cd, ns);
				writer.Close();
				writer=null;
			} 
			catch
			{
			}
		}

		public static void SerializeObject(string filename, dv21_list.CardDefinition cd)
		{
			try 
			{  
				XmlSerializer serializer = 
					new XmlSerializer(typeof(dv21_list.CardDefinition));
		
				// Create an XmlSerializerNamespaces object.
				XmlSerializerNamespaces ns = 
					new XmlSerializerNamespaces();
				// Add two namespaces with prefixes.
				//ns.Add("inventory", "http://www.cpandl.com");
				//ns.Add("money", "http://www.cohowinery.com");

				// Create an XmlTextWriter using a FileStream.
				Stream fs = new FileStream(filename, FileMode.Create);
				System.Xml.XmlWriter writer = 
					new System.Xml.XmlTextWriter(fs, new System.Text.UTF8Encoding());
				// Serialize using the XmlTextWriter.
				serializer.Serialize(writer, cd, ns);
				writer.Close();
				writer=null;
			} 
			catch
			{
			}
		}


		public static dv21.CardDefinition DeSerializeObject(string filename)
		{
			try 
			{  
					dv21.CardDefinition cd;

					// Create an instance of the XmlSerializer.
					XmlSerializer serializer = 
						new XmlSerializer(typeof(dv21.CardDefinition));
					// Reading the XML document requires a FileStream.
					Stream reader= new FileStream(filename,FileMode.Open);
          
					// Call the Deserialize method to restore the object's state.
					cd=(dv21.CardDefinition) serializer.Deserialize(reader);
					reader.Close();
					reader = null;
					return cd;
			} 
			catch
			{
					return null;
			}
		}
		public static dv21_list.CardDefinition DeSerializeObject2(string filename)
		{
			try 
			{  
				dv21_list.CardDefinition cd;

				// Create an instance of the XmlSerializer.
				XmlSerializer serializer = 
					new XmlSerializer(typeof(dv21_list.CardDefinition));
				// Reading the XML document requires a FileStream.
				Stream reader= new FileStream(filename,FileMode.Open);
          
				// Call the Deserialize method to restore the object's state.
				cd=(dv21_list.CardDefinition) serializer.Deserialize(reader);
				reader.Close();
				reader = null;
				return cd;
			} 
			catch
			{
				return null;
			}
		}
	}

	public class MyTreeNode:  TreeNode 
	{
		private Object mBoundObject;
		private System.Windows.Forms.ContextMenu  mMenu;

		public System.Windows.Forms.ContextMenu NodeContextMenu               // Topic is a named parameter
		{
			get 
			{ 
				return mMenu; 
			}
			set 
			{ 

				mMenu = value; 
			}
		}

		public object BoundObject               // Topic is a named parameter
		{
			get 
			{ 
				return mBoundObject; 
			}
			set 
			{ 

				mBoundObject = value; 
			}
		}


		public MyTreeNode():base()
		{}

		public MyTreeNode(string s):base(s)
		{}
		
		public MyTreeNode(string s, int ImageIndex, int SelIndex):base(s,ImageIndex,SelIndex)
		{}


		
	}

}