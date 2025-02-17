using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using dv21;
using dv21_util ;

namespace dv21_load
{
	/// <summary>
	/// Summary description for frmSectionView.
	/// </summary>
	public class frmSectionView : System.Windows.Forms.Form
	{
		private System.Windows.Forms.Button cmdCancel;
		private System.Windows.Forms.Button cmdOK;
		private System.Windows.Forms.TreeView tvStruct;
		private CardDefinition cd;
		private System.Windows.Forms.ImageList imageList1;
		private System.ComponentModel.IContainer components;
		public bool TypeOnly;
		public string ID;

		public frmSectionView()
		{
			//
			// Required for Windows Form Designer support
			//
			InitializeComponent();

			//
			// TODO: Add any constructor code after InitializeComponent call
			//
			TypeOnly =false;
		}

		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if(components != null)
				{
					components.Dispose();
				}
			}
			base.Dispose( disposing );
		}

		#region Windows Form Designer generated code
		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			this.components = new System.ComponentModel.Container();
			System.Resources.ResourceManager resources = new System.Resources.ResourceManager(typeof(frmSectionView));
			this.tvStruct = new System.Windows.Forms.TreeView();
			this.cmdCancel = new System.Windows.Forms.Button();
			this.cmdOK = new System.Windows.Forms.Button();
			this.imageList1 = new System.Windows.Forms.ImageList(this.components);
			this.SuspendLayout();
			// 
			// tvStruct
			// 
			this.tvStruct.ImageList = this.imageList1;
			this.tvStruct.Location = new System.Drawing.Point(8, 16);
			this.tvStruct.Name = "tvStruct";
			this.tvStruct.Size = new System.Drawing.Size(400, 360);
			this.tvStruct.TabIndex = 1;
			// 
			// cmdCancel
			// 
			this.cmdCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
			this.cmdCancel.Location = new System.Drawing.Point(208, 384);
			this.cmdCancel.Name = "cmdCancel";
			this.cmdCancel.Size = new System.Drawing.Size(96, 24);
			this.cmdCancel.TabIndex = 2;
			this.cmdCancel.Text = "Cancel";
			// 
			// cmdOK
			// 
			this.cmdOK.Location = new System.Drawing.Point(312, 384);
			this.cmdOK.Name = "cmdOK";
			this.cmdOK.Size = new System.Drawing.Size(96, 24);
			this.cmdOK.TabIndex = 3;
			this.cmdOK.Text = "OK";
			this.cmdOK.Click += new System.EventHandler(this.cmdOK_Click);
			// 
			// imageList1
			// 
			this.imageList1.ColorDepth = System.Windows.Forms.ColorDepth.Depth8Bit;
			this.imageList1.ImageSize = new System.Drawing.Size(32, 32);
			this.imageList1.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList1.ImageStream")));
			this.imageList1.TransparentColor = System.Drawing.Color.Transparent;
			// 
			// frmSectionView
			// 
			this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
			this.ClientSize = new System.Drawing.Size(416, 413);
			this.Controls.AddRange(new System.Windows.Forms.Control[] {
																		  this.cmdOK,
																		  this.cmdCancel,
																		  this.tvStruct});
			this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
			this.Name = "frmSectionView";
			this.Text = "Section view";
			this.Load += new System.EventHandler(this.frmSectionView_Load);
			this.ResumeLayout(false);

		}
		#endregion


		private void LoadSection(dv21.SectionType[] Sections, MyTreeNode n)
		{
			MyTreeNode n2;
			int i;
			if (Sections !=null)
			{
				for(i=0;i<Sections.Length ;i++)
				{
					if( Sections[i].Name == null || Sections[i].Name[0]==null)
					{
						Sections[i].Name = new LocalizedStringsLocalizedString [1];
						Sections[i].Name[0] = new LocalizedStringsLocalizedString();

					}

					n2 = new MyTreeNode(Sections[i].Name[0].Value +"("+ Sections[i].Name[0].Language +")",10,10);
					n2.BoundObject =Sections[i];
					n.Nodes.Add(n2);  
					LoadSection(Sections[i].Section,n2); 
					
				}
			}
		}

		

		private void LoadTree()
		{
			MyTreeNode n; 
			n = new MyTreeNode("Òèï:" + cd.Alias ,0,0);	
			tvStruct.Nodes.Add(n);
			if(TypeOnly)
				n.BoundObject =cd;		
			else
				LoadSection(cd.Sections,n); 
		
		}

		private void frmSectionView_Load(object sender, System.EventArgs e)
		{

			if (TypeOnly)
				this.Text = "Select type";
			else
				this.Text = "Select section";

			tvStruct.Nodes.Clear(); 
			dv21.DefFile df;
			df = MyUtils.DeSerializeLib(Application.StartupPath + "\\lib.xml");
			int i;
			for(i=0;i<df.Paths.Length;i++)
			{
				cd = null;
				try
				{
					cd = MyUtils.DeSerializeObject(df.Paths[i].Path);
				}
				catch{}
				if (cd !=null)
				{
					LoadTree();	
				}
			}
		}

		private void cmdOK_Click(object sender, System.EventArgs e)
		{
			MyTreeNode n; 
			n = (MyTreeNode) tvStruct.SelectedNode;
			if (n == null) return;
			if (n.BoundObject == null) return;
			if (TypeOnly)
				ID = ((dv21.CardDefinition) n.BoundObject).ID; 
			else
				ID = ((dv21.SectionType) n.BoundObject).ID ; 
			this.DialogResult = System.Windows.Forms.DialogResult.OK ;
			this.Hide();
		}

	}
}
