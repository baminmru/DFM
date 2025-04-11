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
using dv21_load;
using System.Globalization;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json;
using System.Text.Encodings.Web;
using System.Text.Unicode;
using System.Xml;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.TextBox;

namespace dv21_util 
{

	public class MyUtils
	{

		[STAThread]
		static void Main() 
		{
			Application.Run(new frmCard());
		}


		public static MyTreeNode FindCDNode( MyTreeNode n)
		{

			MyTreeNode p = n;

            while (p != null  && p.BoundObject.GetType().ToString() != "dv21.CardDefinition")
			{
				p = (MyTreeNode)p.Parent;
			}

			return p;
        }



        public static MyTreeNode FindSectionNode(MyTreeNode n)
        {

            MyTreeNode p = n;

            while (p != null && p.BoundObject.GetType().ToString() != "dv21.SectionType")
            {
                p = (MyTreeNode)p.Parent;
            }

            return p;
        }

        public static TreeNode SyncToNode(TreeNodeCollection root, object obj)
        {
            MyTreeNode m; int i; TreeNode result;

            for (i = 0; i < root.Count; i++)
            {
                m = (MyTreeNode)root[i];
                if (m.BoundObject == obj)
                {
                    result = m;
                    return result;
                }
                result = SyncToNode(m.Nodes, obj);
                if (result != null) return result;
            }
            return null;

        }


        public static void CollectExpanded(List<string> expandedFrom, TreeNodeCollection nodes)
        {
            foreach (TreeNode node in nodes)
            {
                if (node.IsExpanded) expandedFrom.Add(node.FullPath);
                if (node.Nodes.Count > 0) CollectExpanded(expandedFrom, node.Nodes);
            }
        }



        public static void ExpandNodes(TreeNode node, string nodeFullPath)
        {
            if (node.FullPath == nodeFullPath) node.Expand();
            foreach (TreeNode n in node.Nodes)
            {
                if (n.Nodes.Count > 0) ExpandNodes(n, nodeFullPath);
            }
        }


        public  static dv21.SectionType FindParentSection(dv21.SectionType ss, dv21.SectionType child)
        {

            dv21.SectionType p;
            int i;
            if (child.ID ==null) return null;
            if (ss.Section == null) return null;
            for (i = 0; i < ss.Section.Length; i++)
            {
                if (child.ID.Equals(ss.Section[i].ID))
                {
                    return ss;
                }

                p = FindParentSection(ss.Section[i], child);
                if (p != null)
                    return p;

            }

            return null;
        
        }


        public static dv21.SectionType  FindParentSection(dv21.CardDefinition cd, dv21.SectionType child)
        {

            dv21.SectionType p;
            int i;
            if (child.ID == null) return null;
            if (cd.Sections == null) return null;
            for ( i=0; i < cd.Sections.Length; i++)
            {
                if (child.ID.Equals(cd.Sections[i].ID))
                {
                    return null;
                }

                p = FindParentSection(cd.Sections[i], child);
                if (p != null)
                    return p;

            }
            return null;

        }


        public static bool isRootSection(dv21.CardDefinition cd, dv21.SectionType child)
        {

            dv21.SectionType p;
            int i;
            if (child.ID == null) return false;
            if (cd.Sections == null) return false;

            for (i = 0; i < cd.Sections.Length; i++)
            {
                try
                {
                    if (child.ID.Equals(cd.Sections[i].ID))
                    {
                        return true;
                    }
                }
                catch { 
                
                }

                
            }
            return false;

        }


        public static bool HasIDField(dv21.SectionType s)
        {

            
            int i;
            if (s.Field != null)
                for (i = 0; i < s.Field.Length; i++)
            {
                if (s.Field[i].NotNull && s.Field[i].UseforPK)
                    return true;


            }
            return false;

        }


        public static dv21.FieldType  GetIDField(dv21.SectionType s)
        {

            int i;
            if (s.Field != null)
            {
                for (i = 0; i < s.Field.Length; i++)
                {
                    if (s.Field[i].NotNull && s.Field[i].UseforPK)
                        return s.Field[i];
                }
            }
            return null;

        }


        public static string GetIDType( dv21.SectionType s)
        {

            string idType = s.IdType;
            if (idType == "")
                idType = FieldTypeType.@int.ToString();

            dv21.SectionType p;
            int i;

            if (s.Field != null)
            {
                for (i = 0; i < s.Field.Length; i++)
                {
                    if (s.Field[i].NotNull && s.Field[i].UseforPK)
                        return s.Field[i].Type.ToString();


                }
            }
            return idType;

        }

        public static string GetIDName(dv21.SectionType s)
        {

            string idName = "id";
            dv21.SectionType p;
            int i;

            if (s.Field != null)
            {
                for (i = 0; i < s.Field.Length; i++)
                {
                    if (s.Field[i].NotNull && s.Field[i].UseforPK)
                        return s.Field[i].Alias;


                }
            }
            return idName;

        }



        public static List<dv21.CardDefinition> cards = null;

        public static dv21.SectionType ResolveReference(List<dv21.CardDefinition> cards, String ID) {

            dv21.CardDefinition cd;
            dv21.SectionType p;
            for (int c=0; c<cards.Count; c++) {
                cd = cards[c];
                    
                p = ResolveReference(cd.Sections, ID);
                if (p != null)
                    return p;

            }
            return null;
        }


        public static dv21.SectionType ResolveReference(dv21.SectionType[] ss, String ID)
        {
            dv21.SectionType p;
            int i;
            if (ss == null) return null;
            for (i = 0; i < ss.Length; i++)
            {
                if (ID == ss[i].ID)
                {
                    return ss[i];
                }

                p = ResolveReference(ss[i].Section, ID);
                if (p != null)
                    return p;

            }
            return null;
        }

        public static string mProjectFile;
        public static string ProjectFile
        {
            get { return mProjectFile; }
            set { mProjectFile = value;
                LoadCards();
            }
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


				StringWriter swriter = new StringWriter();
                serializer.Serialize(swriter, cd, ns);
				string sXML = swriter.ToString();
                swriter.Close();
                swriter=null;

                if (sXML  != "")
				{
					if (File.Exists(filename))
					{
						DateTime d = DateTime.Now;
						string fnBack = filename.Replace(".xml", "")
							+"_" + d.ToString("yyddMMHHmmss") + ".bak";

                        File.Copy(filename, fnBack, true);
					}
                    Stream fs = new FileStream(filename, FileMode.Create);
					System.Xml.XmlWriter writer =
						new System.Xml.XmlTextWriter(fs, new System.Text.UTF8Encoding());
					// Serialize using the XmlTextWriter.
					serializer.Serialize(writer, cd, ns);
					writer.Close();
					writer = null;

				}
                else
				{
					MessageBox.Show("XML backup Save Error");
				}


            
    //         
			} 
			catch ( System.Exception e)
			{
                MessageBox.Show("XML Save Error:" + e.InnerException.Message);
            }

			
		}



        public static void SerializeObjectToJSON(string filename, dv21.CardDefinition cd)
        {
            try
            {

                var options = new JsonSerializerOptions
                {
                    //Encoder = JavaScriptEncoder.Create(UnicodeRanges.BasicLatin, UnicodeRanges.Cyrillic),
                    Encoder = JavaScriptEncoder.UnsafeRelaxedJsonEscaping,
                    WriteIndented = true
                };
                string sJSON = JsonSerializer.Serialize<dv21.CardDefinition>( cd, options);
                

                if (sJSON != "")
                {
                    if (File.Exists(filename))
                    {
                        DateTime d = DateTime.Now;
                        string fnBack = filename.Replace(".json", "")
                            + "_" + d.ToString("yyddMMHHmmss") + ".json";

                        File.Copy(filename, fnBack, true);
                    }

                    File.WriteAllText(filename, sJSON);

                }
                else
                {
                    MessageBox.Show("JSON backup Save Error");
                }
            }
            catch (System.Exception e)
            {
                MessageBox.Show("JSON Save Error:" + e.InnerException.Message);
            }


        }


        public static void SerializeObjectToJSON(string filename, dv21.DefFile df)
        {
            try
            {

                var options = new JsonSerializerOptions
                {
                    //Encoder = JavaScriptEncoder.Create(UnicodeRanges.BasicLatin, UnicodeRanges.Cyrillic),
                    Encoder = JavaScriptEncoder.UnsafeRelaxedJsonEscaping,
                    WriteIndented = true
                };

                string sJSON =   JsonSerializer.Serialize<dv21.DefFile>(df, options);
        


                if (sJSON != "")
                {
                    if (File.Exists(filename))
                    {
                        DateTime d = DateTime.Now;
                        string fnBack = filename.Replace(".json", "")
                            + "_" + d.ToString("yyddMMHHmmss") + ".json";

                        File.Copy(filename, fnBack, true);
                    }

                    File.WriteAllText(filename, sJSON);

                }
                else
                {
                    MessageBox.Show("JSON backup Save Error");
                }
            }
            catch (System.Exception e)
            {
                MessageBox.Show("JSON Save Error:" + e.InnerException.Message);
            }


        }



        public static void SerializeObject(string filename, dv21.DefFile  cd)
		{
			try 
			{  
				XmlSerializer serializer = 
					new XmlSerializer(typeof(dv21.DefFile));
        
                XmlSerializerNamespaces ns = 
				new XmlSerializerNamespaces();

				string sXML = "";


                using (var swriter = new StringWriter())
				{
					serializer.Serialize(swriter, cd, ns);
					sXML = swriter.ToString();
					swriter.Close();
				}

                

                if (sXML != "")
                {
                    if (File.Exists(filename))
                    {
                        DateTime d = DateTime.Now;
                        string fnBack = filename.Replace(".xml", "")
                            + "_" + d.ToString("yyddMMHHmmss") + ".bak";

                        File.Copy(filename, fnBack, true);
                    }

                    Stream fs = new FileStream(filename, FileMode.Create);
                    System.Xml.XmlWriter writer =
                        new System.Xml.XmlTextWriter(fs, new System.Text.UTF8Encoding());
                    serializer.Serialize(writer, cd, ns);
                    writer.Close();
                    writer = null;

                }
                else
                {
                    MessageBox.Show("XML backup Save Error");
                }


			
			}
            catch (System.Exception e)
            {
                MessageBox.Show("XML Save Error:" + e.Message);
            }
        }


        



        public static void Tool_WriteFile(string s, string path, string fname, bool Capitalize = false)
        {
            string p = path;
            if (!p.EndsWith("\\"))
            {
                p += "\\";
            }
            DirectoryInfo di = new DirectoryInfo(p);

            if (!di.Exists)
            {
                di.Create();
            }
            File.WriteAllText(p + fname, s, System.Text.Encoding.UTF8);
           
            
        }


        public static string DeCap(string s)
        {
            //if (!UseDeCap)
            //{
            //    return s;
            //}

            string sOut;
            if (!string.IsNullOrEmpty(s))
            {
                sOut = char.ToLower(s[0]) + s.Substring(1);
                return sOut;
            }
            else
            {
                return s;
            }
        }


        /*
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


                public static dv21_list.CardDefinition ArrayToList(dv21.CardDefinition FromObject)
                {
                    dv21_list.CardDefinition ToObject;
                    Stream stm= new MemoryStream();


                    try 
                    {  
                        XmlSerializer serializer1 = 
                            new XmlSerializer(typeof(dv21.CardDefinition));

                        XmlSerializerNamespaces ns = 
                            new XmlSerializerNamespaces();

                        System.Xml.XmlWriter writer = 
                            new System.Xml.XmlTextWriter(stm, new System.Text.UTF8Encoding());
                        serializer1.Serialize(writer, FromObject, ns);
                        stm.Position    =0;
                        XmlSerializer serializer2 = 
                            new XmlSerializer(typeof(dv21_list.CardDefinition));
                        ToObject=(dv21_list.CardDefinition) serializer2.Deserialize(stm);
                        stm.Close();
                        stm = null;
                    }
                    catch{ ToObject=null; }
                    return ToObject;
                }


                public static dv21.CardDefinition ListToArray(dv21_list.CardDefinition FromObject )
                {
                    dv21.CardDefinition ToObject;
                    Stream stm= new MemoryStream();


                    try 
                    {  
                        XmlSerializer serializer1 = 
                            new XmlSerializer(typeof(dv21_list.CardDefinition));

                        XmlSerializerNamespaces ns = 
                            new XmlSerializerNamespaces();

                        System.Xml.XmlWriter writer = 
                            new System.Xml.XmlTextWriter(stm, new System.Text.UTF8Encoding());
                        serializer1.Serialize(writer, FromObject, ns);
                        stm.Position    =0;
                        XmlSerializer serializer2 = 
                            new XmlSerializer(typeof(dv21.CardDefinition));
                        ToObject=(dv21.CardDefinition) serializer2.Deserialize(stm);
                        stm.Close();
                        stm = null;
                    }
                    catch{ToObject=null;}
                    return ToObject;
                }
        */
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

        public static dv21.CardDefinition DeSerializeObjectFromJSON(string filename)
        {
            try
            {
                ;


                string jsonString = File.ReadAllText(filename);
                dv21.CardDefinition cd = JsonSerializer.Deserialize<dv21.CardDefinition>(jsonString);
                
                return cd;
            }
            catch
            {
                return null;
            }
        }


        public static dv21.DefFile DeSerializeLib(string filename)
		{
			try 
			{  
				dv21.DefFile cd;
				
				XmlSerializer serializer = 
					new XmlSerializer(typeof(dv21.DefFile));
				
				Stream reader= new FileStream(filename,FileMode.Open);
				cd=(dv21.DefFile) serializer.Deserialize(reader);
				reader.Close();
				reader = null;
				return cd;
			} 
			catch
			{
				return null;
			}
		}

        public static dv21.DefFile DeSerializeLibFromJSON(string filename)
        {
            try
            {
                dv21.DefFile cd;
                cd = JsonSerializer.Deserialize< dv21.DefFile>(filename);
                return cd;
            }
            catch
            {
                return null;
            }
        }




        /*		public static dv21_list.CardDefinition DeSerializeObject2(string filename)
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
                */


        public static Array Add(Array arr, Object Item,Array arr2)
		{
			if(arr !=null)
			{
				ArrayList lsa = new ArrayList();
				lsa.InsertRange(0,arr);
				lsa.Add(Item);
				lsa.CopyTo(arr2,0);
				lsa=null;
			}
			return arr2;
		}

		public static Array RemoveAt(Array arr, int Index, Array arr2)
		{
			if(arr !=null)
			{
				ArrayList lsa = new ArrayList();
				lsa.InsertRange(0,arr);
				lsa.RemoveAt(Index);
				lsa.CopyTo(arr2,0);
				lsa=null;
			}
			return arr2;
		}
		public static Array Remove(Array arr, Object Item, Array arr2)
		{
			if(arr !=null)
			{
				ArrayList lsa = new ArrayList();
				lsa.InsertRange(0,arr);
				lsa.Remove(Item);
				lsa.CopyTo(arr2,0);
				lsa=null;
			}
			return arr2;
		}



        public static void LoadCards()
        {

            cards = new List<dv21.CardDefinition>();

            dv21.DefFile df = null;
            CardDefinition ot = null;
            try
            {
                df = MyUtils.DeSerializeLib(ProjectFile);
            }
            catch
            {
            }
            if (df == null) return;
            int i;
            for (i = 0; i < df.Paths.Count; i++)
            {
                ot = null;
                try
                {
                    ot = MyUtils.DeSerializeObject(df.Paths[i]);
                }
                catch { }
                if (ot != null)
                {
                    cards.Add(ot);

                }
            }
        }



        public static void LoadCards(List<dv21.CardDefinition> cards)
        {

            dv21.DefFile df = null;
            CardDefinition ot = null;
            try
            {
                df = MyUtils.DeSerializeLib(ProjectFile);
            }
            catch
            {
            }
            int i;
            for (i = 0; i < df.Paths.Count; i++)
            {
                ot = null;
                try
                {
                    ot = MyUtils.DeSerializeObject(df.Paths[i]);
                }
                catch { }
                if (ot != null)
                {
                    cards.Add(ot);

                }
            }
        }


        public static void LoadCards(List<dv21.CardDefinition> cards, string ProjectFile)
        {

            dv21.DefFile df = null;
            CardDefinition ot = null;
            try
            {

                if(ProjectFile.Contains(".xml"))
                    df = MyUtils.DeSerializeLib(ProjectFile);
                if (ProjectFile.Contains(".json"))
                    df = MyUtils.DeSerializeLibFromJSON(ProjectFile);
            }
            catch
            {
            }
            int i;
            for (i = 0; i < df.Paths.Count; i++)
            {
                ot = null;
                try
                {
                    ot = MyUtils.DeSerializeObject(df.Paths[i]);
                }
                catch { }
                if (ot != null)
                {
                    cards.Add(ot);

                }
            }
        }


        public static CardDefinition GetReferencedType(CardDefinition cd, string RefType)
        {

            if(cd != null)
                if (cd.ID == RefType)
                    return cd;

            CardDefinition refCD;

            int i;
            for (i = 0; i < MyUtils.cards.Count; i++)
            {
                refCD = null;
                try
                {
                    refCD = MyUtils.cards[i];
                }
                catch { }
                if (refCD != null)
                {
                    if (refCD.ID == RefType)
                        return refCD;
                }
            }
            return null;
        }



        public static CardDefinition GetReferencedType(List<CardDefinition> cards, string RefType)
        {

            CardDefinition refCD;

            int i;
            for (i = 0; i < cards.Count; i++)
            {
                refCD = null;
                try
                {
                    refCD = cards[i];
                }
                catch { }
                if (refCD != null)
                {
                    if (refCD.ID == RefType)
                        return refCD;
                }
            }
            return null;
        }


        public static CardDefinition GetReferencedTypeByName(List<CardDefinition> cards, string TypeAlias)
        {

            CardDefinition refCD;

            int i;
            for (i = 0; i < cards.Count; i++)
            {
                refCD = null;
                try
                {
                    refCD = cards[i];
                }
                catch { }
                if (refCD != null)
                {
                    if (refCD.Alias.ToLower() == TypeAlias.ToLower())
                        return refCD;
                }
            }
            return null;
        }




        public static SectionType GetReferencedSection(SectionType[] Sections, string RefSection)
        {
            SectionType st;
			if (Sections == null) return null;
            for (int i = 0; i < Sections.Length; i++)
            {
                st = Sections[i];
                if (st.ID == RefSection)
                    return st;
                else
                {
                    st = GetReferencedSection(st.Section, RefSection);
                    if (st != null) return st;
                }
            }

            return null;
        }




        public static SectionType GetReferencedSectionByName(SectionType[] Sections, string SectionAlias)
        {
            SectionType st;
            if (Sections == null) return null;
            for (int i = 0; i < Sections.Length; i++)
            {
                st = Sections[i];
                if (st.Alias.ToLower() == SectionAlias.ToLower())
                    return st;
                else
                {
                    st = GetReferencedSectionByName(st.Section, SectionAlias);
                    if (st != null) return st;
                }
            }

            return null;
        }



        public static string GetSectionBrief(SectionType St)
        {
            if (St == null) return "";
            if (St.Field  == null) return "";

            for (int i = 0; i < St.Field.Length; i++)
            {
                
                if (St.Field[i].IsBrief)
				{
					return St.Field[i].Alias;

                }
                    
            }

            return "";
        }


        public static string C1(string title)
        {

			//return CultureInfo.CurrentCulture.TextInfo.ToTitleCase(title.Replace("_",""));
			if (title == null) return "XXX";

            StringBuilder result = new StringBuilder(title.Length);
            bool makeUpper = true;
            foreach (var c in title)
            {
				if (makeUpper)
				{
					result.Append(Char.ToUpper(c));
					makeUpper = false;
				}
				else
				{
					if (c == '_')
					{
						makeUpper = true;
					}
					else
					{
						result.Append(c);
					}
				}
            }
			return result.ToString();

        }


        public static string C2(string title)
        {

            
            if (title == null) return "XXX";

            StringBuilder result = new StringBuilder(title.Length);
            bool makeUpper = false;
            foreach (var c in title)
            {
                if (makeUpper)
                {
                    result.Append(Char.ToUpper(c));
                    makeUpper = false;
                }
                else
                {
                    if (c == '_')
                    {
                        makeUpper = true;
                    }
                    else
                    {
                        result.Append(c);
                    }
                }
            }
            return result.ToString();

        }


    }

	public class MyTreeNode:  TreeNode 
	{
		private Object mBoundObject;
		private System.Windows.Forms.ContextMenu  mMenu;
        private String mPath;

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


        public String Path               
        {
            get
            {
                return mPath;
            }
            set
            {

                mPath = value;
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
