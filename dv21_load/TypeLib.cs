using System;
using System.Drawing;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Windows.Forms;
using dv21;
using dv21_util; 


//#define ADDROW(Type,Arr) ArrayList lsa = new ArrayList(); lsa.InsertRange(0,Arr); lsa.Add(new Type() ); Arr = new Type[lsa.Count]; lsa.CopyTo(Arr,0);

namespace dv21_tl
{
	/// <summary>
	/// Summary description for TypeLibEditor.
	/// </summary>
	public class TypeLibEditor : System.Windows.Forms.Form
	{
		private System.Windows.Forms.ListBox lstStrings;
		private System.Windows.Forms.Button cmdAdd;
		private System.Windows.Forms.Button cmdDel;
		private System.Windows.Forms.TextBox txtValue;
		private List<string> mDefFilePaths;
		private bool inClick;
		private System.Windows.Forms.Button cmdOK;
		private System.Windows.Forms.Label label1;
		private System.Windows.Forms.OpenFileDialog dlgOpen;
		private System.Windows.Forms.Button cmdFile;
		private System.Windows.Forms.Label label2;

		public List<string>   DefFilePaths
		{
			get 
			{
				return mDefFilePaths;
			}
			set 
			{
				mDefFilePaths=value;
			}
		}

		/// <summary>
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		public TypeLibEditor()
		{
			//
			// Required for Windows Form Designer support
			//
			InitializeComponent();

			//
			// TODO: Add any constructor code after InitializeComponent call
			//
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(TypeLibEditor));
            this.lstStrings = new System.Windows.Forms.ListBox();
            this.cmdAdd = new System.Windows.Forms.Button();
            this.cmdDel = new System.Windows.Forms.Button();
            this.txtValue = new System.Windows.Forms.TextBox();
            this.cmdOK = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.dlgOpen = new System.Windows.Forms.OpenFileDialog();
            this.cmdFile = new System.Windows.Forms.Button();
            this.label2 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // lstStrings
            // 
            this.lstStrings.Location = new System.Drawing.Point(16, 48);
            this.lstStrings.Name = "lstStrings";
            this.lstStrings.Size = new System.Drawing.Size(384, 82);
            this.lstStrings.TabIndex = 8;
            this.lstStrings.SelectedIndexChanged += new System.EventHandler(this.lstStrings_SelectedIndexChanged);
            // 
            // cmdAdd
            // 
            this.cmdAdd.Image = ((System.Drawing.Image)(resources.GetObject("cmdAdd.Image")));
            this.cmdAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.cmdAdd.Location = new System.Drawing.Point(216, 16);
            this.cmdAdd.Name = "cmdAdd";
            this.cmdAdd.Size = new System.Drawing.Size(88, 24);
            this.cmdAdd.TabIndex = 6;
            this.cmdAdd.Text = "&Add";
            this.cmdAdd.Click += new System.EventHandler(this.cmdAdd_Click);
            // 
            // cmdDel
            // 
            this.cmdDel.Image = ((System.Drawing.Image)(resources.GetObject("cmdDel.Image")));
            this.cmdDel.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.cmdDel.Location = new System.Drawing.Point(312, 16);
            this.cmdDel.Name = "cmdDel";
            this.cmdDel.Size = new System.Drawing.Size(88, 24);
            this.cmdDel.TabIndex = 7;
            this.cmdDel.Text = "&Delete";
            this.cmdDel.Click += new System.EventHandler(this.cmdDel_Click);
            // 
            // txtValue
            // 
            this.txtValue.Location = new System.Drawing.Point(16, 168);
            this.txtValue.Name = "txtValue";
            this.txtValue.Size = new System.Drawing.Size(336, 20);
            this.txtValue.TabIndex = 3;
            this.txtValue.TextChanged += new System.EventHandler(this.txtValue_TextChanged);
            // 
            // cmdOK
            // 
            this.cmdOK.DialogResult = System.Windows.Forms.DialogResult.OK;
            this.cmdOK.Location = new System.Drawing.Point(336, 208);
            this.cmdOK.Name = "cmdOK";
            this.cmdOK.Size = new System.Drawing.Size(64, 24);
            this.cmdOK.TabIndex = 4;
            this.cmdOK.Text = "OK";
            this.cmdOK.Click += new System.EventHandler(this.cmdOK_Click);
            // 
            // label1
            // 
            this.label1.Location = new System.Drawing.Point(16, 16);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(184, 16);
            this.label1.TabIndex = 9;
            this.label1.Text = "Project fiels";
            // 
            // dlgOpen
            // 
            this.dlgOpen.Filter = "JSON file|*.json|XML file|*.xml|All files|*.*";
            // 
            // cmdFile
            // 
            this.cmdFile.Location = new System.Drawing.Point(360, 168);
            this.cmdFile.Name = "cmdFile";
            this.cmdFile.Size = new System.Drawing.Size(32, 24);
            this.cmdFile.TabIndex = 10;
            this.cmdFile.Text = "...";
            this.cmdFile.Click += new System.EventHandler(this.cmdFile_Click);
            // 
            // label2
            // 
            this.label2.Location = new System.Drawing.Point(16, 144);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(144, 24);
            this.label2.TabIndex = 11;
            this.label2.Text = "File";
            // 
            // TypeLibEditor
            // 
            this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
            this.ClientSize = new System.Drawing.Size(410, 239);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.cmdFile);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.cmdOK);
            this.Controls.Add(this.txtValue);
            this.Controls.Add(this.cmdDel);
            this.Controls.Add(this.cmdAdd);
            this.Controls.Add(this.lstStrings);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "TypeLibEditor";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            this.Text = "Project Editor";
            this.Load += new System.EventHandler(this.TypeLibEditor_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

		}
		#endregion

		private void TypeLibEditor_Load(object sender, System.EventArgs e)
		{
		
			inClick = false;
			InitList();
			try
			{
				lstStrings.SelectedIndex =0;
			}
			catch{}	;
		}

		public void InitList()
		{
			if (DefFilePaths != null)
			{
				inClick = true;
				lstStrings.Items.Clear();
				int i;
				String ls;
				
				for(i=0;i<DefFilePaths.Count ;i++)
				{
					ls=DefFilePaths[i];
					lstStrings.Items.Add(ls) ;
				}
				inClick = false;
			}
		}

		private void cmdAdd_Click(object sender, System.EventArgs e)
		{
			if(DefFilePaths !=null)
			{
				
				DefFilePaths.Add("C:\\");
				lstStrings.SelectedIndex = lstStrings.Items.Add("c:\\");
			}
		}

		private void cmdDel_Click(object sender, System.EventArgs e)
		{
			if(DefFilePaths !=null)
			{
				if(lstStrings.SelectedIndex >=0) 
				{

				
					DefFilePaths.RemoveAt(lstStrings.SelectedIndex);
					InitList();
				}
			}
		}

		private void cmbLang_SelectedIndexChanged(object sender, System.EventArgs e)
		{
		
		}

		private void txtValue_TextChanged(object sender, System.EventArgs e)
		{
			if (!inClick)
			{
				if(lstStrings.SelectedIndex >=0) 
				{

					int i=lstStrings.SelectedIndex;
					String ls;
					ls = DefFilePaths[i];
					ls=txtValue.Text; 
					lstStrings.Items[i]=ls;
					DefFilePaths[i] = ls;


                }
			}
		}

		private void lstStrings_SelectedIndexChanged(object sender, System.EventArgs e)
		{
			inClick = true;
			try
			{
				int i=lstStrings.SelectedIndex;
				String ls;
				ls = DefFilePaths[i];
				txtValue.Text=ls;
			}
			catch{}
			inClick = false;
		}

		private void cmdOK_Click(object sender, System.EventArgs e)
		{
			this.Hide(); 
		}

		private void cmdFile_Click(object sender, System.EventArgs e)
		{

			try
			{
				if (dlgOpen.ShowDialog()==System.Windows.Forms.DialogResult.OK ) 
					txtValue.Text= dlgOpen.FileName; 
			}
			catch{}
		}

		

		
	}
}
