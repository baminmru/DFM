using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Windows.Forms;
using dv21;
using dv21_util;
using dv21_ls;
using dv21_load;

namespace dv21_ctl
{
	/// <summary>
	/// Summary description for ctlviewColumn.
	/// </summary>
	public class ctlviewColumn : System.Windows.Forms.UserControl
	{
		private System.Windows.Forms.Label label1;
		private System.Windows.Forms.ListBox cmb1Names;
		private System.Windows.Forms.Label label5;
		private System.Windows.Forms.Button cmd1Names;
		private System.Windows.Forms.Button cmd1NewID;
		private System.Windows.Forms.TextBox txt1ID;
		private System.Windows.Forms.TextBox txt1Alias;
		private System.Windows.Forms.Label label4;
		private System.Windows.Forms.Label label3;
		private bool inLoad;
		private ViewColumnType mColumn;
		public MyTreeNode LastNode;

		private void UpdateNode()
		{
			LastNode.Text=  mColumn.Name[0].Value +"(" + mColumn.Name[0].Language + ")" ;
            frmCard f = (frmCard)this.ParentForm;
            f.Saved = false;
		}
		/// <summary> 
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		public ViewColumnType Column{
			get{
				return mColumn;
			}
			set{
				mColumn = value;
				if(mColumn!=null)
				{
					inLoad = true;
					txt1Alias.Text =mColumn.Alias ;
					txt1ID.Text = mColumn.ID;
					cmb1Names.Items.Clear(); 
					int i;
					if (mColumn.Name!=null)
					{	
						for(i=0;i<mColumn.Name.Length  ;i++)
						{
							cmb1Names.Items.Add(mColumn.Name[i].Value +"(" +mColumn.Name[i].Language  +")" );
						}
					}
					inLoad = false;
					
				}
			}
		}

		public ctlviewColumn()
		{
			// This call is required by the Windows.Forms Form Designer.
			InitializeComponent();

			// TODO: Add any initialization after the InitForm call

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

		#region Component Designer generated code
		/// <summary> 
		/// Required method for Designer support - do not modify 
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			this.label1 = new System.Windows.Forms.Label();
			this.cmb1Names = new System.Windows.Forms.ListBox();
			this.label5 = new System.Windows.Forms.Label();
			this.cmd1Names = new System.Windows.Forms.Button();
			this.cmd1NewID = new System.Windows.Forms.Button();
			this.txt1ID = new System.Windows.Forms.TextBox();
			this.txt1Alias = new System.Windows.Forms.TextBox();
			this.label4 = new System.Windows.Forms.Label();
			this.label3 = new System.Windows.Forms.Label();
			this.SuspendLayout();
			// 
			// label1
			// 
			this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((System.Byte)(204)));
			this.label1.Name = "label1";
			this.label1.Size = new System.Drawing.Size(224, 32);
			this.label1.TabIndex = 0;
			this.label1.Text = "Колонка ";
			// 
			// cmb1Names
			// 
			this.cmb1Names.Enabled = false;
			this.cmb1Names.Location = new System.Drawing.Point(16, 136);
			this.cmb1Names.Name = "cmb1Names";
			this.cmb1Names.Size = new System.Drawing.Size(224, 95);
			this.cmb1Names.TabIndex = 6;
			// 
			// label5
			// 
			this.label5.Location = new System.Drawing.Point(16, 120);
			this.label5.Name = "label5";
			this.label5.Size = new System.Drawing.Size(80, 16);
			this.label5.TabIndex = 5;
			this.label5.Text = "Названия";
			// 
			// cmd1Names
			// 
			this.cmd1Names.Location = new System.Drawing.Point(248, 136);
			this.cmd1Names.Name = "cmd1Names";
			this.cmd1Names.Size = new System.Drawing.Size(40, 24);
			this.cmd1Names.TabIndex = 7;
			this.cmd1Names.Text = "...";
			this.cmd1Names.Click += new System.EventHandler(this.cmd1Names_Click);
			// 
			// cmd1NewID
			// 
			this.cmd1NewID.Location = new System.Drawing.Point(248, 88);
			this.cmd1NewID.Name = "cmd1NewID";
			this.cmd1NewID.Size = new System.Drawing.Size(40, 24);
			this.cmd1NewID.TabIndex = 4;
			this.cmd1NewID.Text = "new";
			this.cmd1NewID.Click += new System.EventHandler(this.cmd1NewID_Click);
			// 
			// txt1ID
			// 
			this.txt1ID.Location = new System.Drawing.Point(16, 88);
			this.txt1ID.Name = "txt1ID";
			this.txt1ID.Size = new System.Drawing.Size(224, 20);
			this.txt1ID.TabIndex = 3;
			this.txt1ID.Text = "";
			this.txt1ID.TextChanged += new System.EventHandler(this.txt1ID_TextChanged);
			// 
			// txt1Alias
			// 
			this.txt1Alias.Location = new System.Drawing.Point(16, 48);
			this.txt1Alias.Name = "txt1Alias";
			this.txt1Alias.Size = new System.Drawing.Size(272, 20);
			this.txt1Alias.TabIndex = 1;
			this.txt1Alias.Text = "";
			this.txt1Alias.TextChanged += new System.EventHandler(this.txt1Alias_TextChanged);
			// 
			// label4
			// 
			this.label4.Location = new System.Drawing.Point(16, 72);
			this.label4.Name = "label4";
			this.label4.Size = new System.Drawing.Size(88, 16);
			this.label4.TabIndex = 2;
			this.label4.Text = "Идентификатор";
			// 
			// label3
			// 
			this.label3.Location = new System.Drawing.Point(16, 32);
			this.label3.Name = "label3";
			this.label3.Size = new System.Drawing.Size(96, 16);
			this.label3.TabIndex = 0;
			this.label3.Text = "Псевдоним";
			// 
			// ctlviewColumn
			// 
			this.Controls.AddRange(new System.Windows.Forms.Control[] {
																		  this.cmb1Names,
																		  this.label5,
																		  this.cmd1Names,
																		  this.cmd1NewID,
																		  this.txt1ID,
																		  this.txt1Alias,
																		  this.label4,
																		  this.label3,
																		  this.label1});
			this.Name = "ctlviewColumn";
			this.Size = new System.Drawing.Size(312, 288);
			this.Load += new System.EventHandler(this.ctlviewColumn_Load);
			this.ResumeLayout(false);

		}
		#endregion

		private void cmd1NewID_Click(object sender, System.EventArgs e)
		{
			txt1ID.Text = System.Guid.NewGuid().ToString();

		}

		private void cmd1Names_Click(object sender, System.EventArgs e)
		{
			
			if (mColumn.Name!=null)
			{
				LStringEditor f = new LStringEditor();
				f.LString=	mColumn.Name ;
				f.InitList();
				f.ShowDialog();
				mColumn.Name = f.LString; 
				int i;
				cmb1Names.Items.Clear();
				dv21.LocalizedStringsLocalizedString ls;
				for(i=0;i<mColumn.Name.Length  ;i++)
				{
					ls=(dv21.LocalizedStringsLocalizedString) (mColumn.Name[i]);
					cmb1Names.Items.Add(ls.Value +"(" +ls.Language  +")" );
				}
				UpdateNode();
			}
						
		}

		private void txt1Alias_TextChanged(object sender, System.EventArgs e)
		{
			if(!inLoad)
			{
				mColumn.Alias =txt1Alias.Text;
				UpdateNode();
			}
		}

		private void txt1ID_TextChanged(object sender, System.EventArgs e)
		{
			if(!inLoad)
			{
				mColumn.ID =txt1ID.Text;
				UpdateNode();
			}
		}

		private void ctlviewColumn_Load(object sender, System.EventArgs e)
		{
			inLoad = false;
		}

	}
}
