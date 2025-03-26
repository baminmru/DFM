using dv21;
using dv21_ctl;
using dv21_util;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace dv21_load
{
    public partial class frmCopy: Form
    {
        public frmCopy()
        {
            InitializeComponent();
        }

        private CardDefinition cd;

        private void frmCopy_Load(object sender, EventArgs e)
        {

            ReloadTree();

        }


       

        


        private void ReloadTree()
        {

            object SyncTo = ((MyTreeNode)tvStructTo.SelectedNode).BoundObject;

            List<string> expandedFrom;
            List<string> expandedTo;

            expandedFrom = new List<String>();
            expandedTo = new List<String>();

            MyUtils.CollectExpanded(expandedFrom,tvStructFrom.Nodes);
            MyUtils.CollectExpanded(expandedTo,tvStructTo.Nodes);

            tvStructFrom.Nodes.Clear();
            tvStructTo.Nodes.Clear();

            dv21.DefFile df;
            df = MyUtils.DeSerializeLib(MyUtils.ProjectFile);
            int i;
            for (i = 0; i < df.Paths.Length; i++)
            {
                cd = null;
                try
                {
                    cd = MyUtils.DeSerializeObject(df.Paths[i].Path);
                }
                catch { }
                if (cd != null)
                {
                    LoadTree(tvStructFrom, "");
                    LoadTree(tvStructTo, df.Paths[i].Path);
                }
            }

            foreach (TreeNode node in tvStructFrom.Nodes)
            {
                foreach (var path in expandedFrom)
                {
                    MyUtils.ExpandNodes(node, path);
                }
            }


            foreach (TreeNode node in tvStructTo.Nodes)
            {
                foreach (var path in expandedTo)
                {
                    MyUtils.ExpandNodes(node, path);
                }
            }

            if (SyncTo != null)
            {
                tvStructTo.SelectedNode = MyUtils.SyncToNode(tvStructTo.Nodes, SyncTo);
            }

        }

       


        private void LoadSection(dv21.SectionType[] Sections, MyTreeNode n, string path)
        {
            MyTreeNode n2;
            int i;
            if (Sections != null)
            {
                for (i = 0; i < Sections.Length; i++)
                {
                    if (Sections[i].Name == null || Sections[i].Name[0] == null)
                    {
                        Sections[i].Name = new LocalizedStringsLocalizedString[1];
                        Sections[i].Name[0] = new LocalizedStringsLocalizedString();

                    }

                    n2 = new MyTreeNode(Sections[i].Name[0].Value + "(" + Sections[i].Name[0].Language + ")", 10, 10);
                    n2.BoundObject = Sections[i];
                    n2.Path = path;
                    n.Nodes.Add(n2);
                    LoadSectionFields(Sections[i], n2, path);

                    LoadSection(Sections[i].Section, n2, path);

                }
            }
        }


        private void LoadSectionFields(dv21.SectionType s, MyTreeNode n, string path)
        {
            MyTreeNode n2, n3;
            int i, j;
            if (s != null && s.Field != null)
            {
                for (i = 0; i < s.Field.Length; i++)
                {

                    if (s.Field[i].Name == null || s.Field[i].Name[0] == null)
                    {
                        s.Field[i].Name = new LocalizedStringsLocalizedString[1];
                        s.Field[i].Name[0] = new LocalizedStringsLocalizedString();
                    }


                    n2 = new MyTreeNode(s.Field[i].Name[0].Value + "(" + s.Field[i].Name[0].Language + ")", 9, 9);
                    n2.BoundObject = s.Field[i];
                    n2.Path = path;
                    //n2.NodeContextMenu = mnuField;
                    n.Nodes.Add(n2);


                }
            }
        }


        private void LoadTree(TreeView tv, string path)
        {
            MyTreeNode n;
            n = new MyTreeNode("Тип:" + cd.Alias, 0, 0);
            n.BoundObject = cd;
            n.Path = path;
            tv.Nodes.Add(n);
            LoadSection(cd.Sections, n, path);

        }

        private void cmdCopy_Click(object sender, EventArgs e)
        {
            if (tvStructFrom.SelectedNode != null && tvStructTo.SelectedNode != null)
            {
                MyTreeNode nFrom;
                MyTreeNode nTo;

                nFrom = (MyTreeNode)tvStructFrom.SelectedNode;
                nTo = (MyTreeNode)tvStructTo.SelectedNode;

                Boolean OK = false;
                String sFrom = "";


                if (nFrom != null && nFrom.BoundObject != null)
                {

                    switch (nFrom.BoundObject.GetType().ToString())
                    {
                       
                        case "dv21.SectionType":
                            OK = true;
                            sFrom = "S";
                            break;

                        case "dv21.FieldType":
                            OK = true;
                            sFrom = "F";
                            break;

                        default:
                            System.Windows.Forms.MessageBox.Show("Select section or field at From-tree");
                            break;
                    }
                }

                if (OK && nTo != null && nTo.BoundObject != null)
                {
                    switch (nTo.BoundObject.GetType().ToString())
                    {
                        case "dv21.CardDefinition":
                            if (sFrom == "S")
                            {
                                OK = true;

                                dv21.CardDefinition cd = (dv21.CardDefinition)nTo.BoundObject;
                                dv21.SectionType s = (dv21.SectionType)nFrom.BoundObject;



                                if (cd.Sections != null)
                                {
                                    cd.Sections =(SectionType[]) MyUtils.Add(cd.Sections, s, new SectionType[cd.Sections.Length + 1]);
                                }
                                else
                                {
                                    cd.Sections = new SectionType[1];
                                    cd.Sections[0] = s;
                                }

                                MyUtils.SerializeObject(nTo.Path, cd);

                             

                            }
                            if (sFrom == "F")
                            {
                                OK = false;
                                System.Windows.Forms.MessageBox.Show("Select section at To-tree for copy field");
                            }
                            break;
                     
                        case "dv21.SectionType":

                            if (sFrom == "S")
                            {
                                

                                MyTreeNode cdNode = MyUtils.FindCDNode(nTo);

                                dv21.CardDefinition cd = (dv21.CardDefinition)cdNode.BoundObject;

                                dv21.SectionType ss = (dv21.SectionType)nTo.BoundObject;

                                dv21.SectionType s = (dv21.SectionType)nFrom.BoundObject;


                                if (ss.Section != null)
                                {
                                    ss.Section = (SectionType[])MyUtils.Add(ss.Section, s, new SectionType[cd.Sections.Length + 1]);
                                }
                                else
                                {
                                    ss.Section = new SectionType[1];
                                    ss.Section[0] = s;
                                }

                                MyUtils.SerializeObject(nTo.Path, cd);


                                OK = true;
                            }

                            if (sFrom == "F")
                            {


                                MyTreeNode cdNode = MyUtils.FindCDNode(nTo);
                                MyTreeNode sNode = MyUtils.FindSectionNode(nTo);

                                dv21.CardDefinition cd = (dv21.CardDefinition)cdNode.BoundObject;
                                dv21.SectionType s = (dv21.SectionType)sNode.BoundObject;

                                dv21.FieldType f = (dv21.FieldType)nFrom.BoundObject;


                                if (s.Field != null)
                                {
                                    s.Field = (FieldType[])MyUtils.Add(s.Field, f, new FieldType[s.Field.Length + 1]);
                                }
                                else
                                {
                                    s.Field = new FieldType[1];
                                    s.Field[0] = f;
                                }

                                MyUtils.SerializeObject(nTo.Path, cd);


                                OK = true;
                            }


                            break;

             

                        default:
                            OK = false;
                            System.Windows.Forms.MessageBox.Show("Select card or section at To-tree");
                            break;
                    }
                }

                if (OK)
                {
                    ReloadTree();
                    MyUtils.LoadCards();
                }




            }
            else
            {
                System.Windows.Forms.MessageBox.Show("Select section or field at From-tree \n Select card or section at To-tree");
            }
        }
    }
}
