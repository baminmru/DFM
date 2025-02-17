using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Windows.Forms;
using dv21;
using dv21_util;
using dv21_ls;

namespace dv21_ctl
{
	/// <summary>
	/// Summary description for ctlModeType.
	/// </summary>
	public class ctlModeType : System.Windows.Forms.UserControl
	{
		private System.Windows.Forms.Label label1;
		private System.Windows.Forms.CheckBox chkDefault;
		private System.Windows.Forms.ListBox cmb1Names;
		private System.Windows.Forms.Button cmd1Names;
		private System.Windows.Forms.Button cmd1NewID;
		private System.Windows.Forms.TextBox txt1ID;
		private System.Windows.Forms.Label label4;
		private System.Windows.Forms.Label label3;
		private bool inLoad;
		private ModeType  mMode;
		private System.Windows.Forms.Label label5;
		public MyTreeNode LastNode;

		private void UpdateNode()
		{
			LastNode.Text=  mMode.Name[0].Value + "(" + mMode.Name[0].Language + ")" ;
		}

		/// <summary> 
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		public ctlModeType()
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
			this.chkDefault = new System.Windows.Forms.CheckBox();
			this.cmb1Names = new System.Windows.Forms.ListBox();
			this.cmd1Names = new System.Windows.Forms.Button();
			this.cmd1NewID = new System.Windows.Forms.Button();
			this.txt1ID = new System.Windows.Forms.TextBox();
			this.label4 = new System.Windows.Forms.Label();
			this.label3 = new System.Windows.Forms.Label();
			this.label5 = new System.Windows.Forms.Label();
			this.SuspendLayout();
			// 
			// label1
			// 
			this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((System.Byte)(204)));
			this.label1.Name = "label1";
			this.label1.Size = new System.Drawing.Size(224, 24);
			this.label1.TabIndex = 0;
			this.label1.Text = "Режим";
			// 
			// chkDefault
			// 
			this.chkDefault.Location = new System.Drawing.Point(16, 48);
			this.chkDefault.Name = "chkDefault";
			this.chkDefault.Size = new System.Drawing.Size(112, 16);
			this.chkDefault.TabIndex = 1;
			this.chkDefault.CheckedChanged += new System.EventHandler(this.chkDefault_CheckedChanged);
			// 
			// cmb1Names
			// 
			this.cmb1Names.Enabled = false;
			this.cmb1Names.Location = new System.Drawing.Point(16, 136);
			this.cmb1Names.Name = "cmb1Names";
			this.cmb1Names.Size = new System.Drawing.Size(224, 95);
			this.cmb1Names.TabIndex = 6;
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
			this.label3.Size = new System.Drawing.Size(232, 16);
			this.label3.TabIndex = 0;
			this.label3.Text = "Все действия разрешены";
			// 
			// label5
			// 
			this.label5.Location = new System.Drawing.Point(16, 120);
			this.label5.Name = "label5";
			this.label5.Size = new System.Drawing.Size(80, 16);
			this.label5.TabIndex = 5;
			this.label5.Text = "Названия";
			// 
			// ctlModeType
			// 
			this.Controls.AddRange(new System.Windows.Forms.Control[] {
																		  this.label5,
																		  this.chkDefault,
																		  this.cmb1Names,
																		  this.cmd1Names,
																		  this.cmd1NewID,
																		  this.txt1ID,
																		  this.label4,
																		  this.label3,
																		  this.label1});
			this.Name = "ctlModeType";
			this.Size = new System.Drawing.Size(312, 272);
			this.Load += new System.EventHandler(this.ctlModeType_Load);
			this.ResumeLayout(false);

		}
		#endregion

		private void ctlModeType_Load(object sender, System.EventArgs e)
		{
			inLoad = false;
		}

		public ModeType Mode
		{
			get
			{
				return mMode;
			}
			set
			{
				mMode = value;
				if(mMode!=null)
				{
					inLoad = true;

					txt1ID.Text = mMode.ID;
					chkDefault.Checked =mMode.AllowAllActions ; 
					cmb1Names.Items.Clear(); 
					int i;
					if (mMode.Name!=null)
					{	
						for(i=0;i<mMode.Name.Length  ;i++)
						{
							cmb1Names.Items.Add(mMode.Name[i].Value +"(" +mMode.Name[i].Language  +")" );
						}
					}
					inLoad = false;
				}
			}
		}


		private void cmd1NewID_Click(object sender, System.EventArgs e)
		{
			txt1ID.Text = System.Guid.NewGuid().ToString();
		}

		private void cmd1Names_Click(object sender, System.EventArgs e)
		{
			if (mMode.Name!=null)
			{
				LStringEditor f = new LStringEditor();
				f.LString=	mMode.Name ;
				f.InitList();
				f.ShowDialog();
				mMode.Name = f.LString; 
				int i;
				cmb1Names.Items.Clear();
				dv21.LocalizedStringsLocalizedString ls;
				for(i=0;i<mMode.Name.Length  ;i++)
				{
					ls=(dv21.LocalizedStringsLocalizedString) (mMode.Name[i]);
					cmb1Names.Items.Add(ls.Value +"(" +ls.Language  +")" );
				}
				UpdateNode();
			}
		}

		private void txt1ID_TextChanged(object sender, System.EventArgs e)
		{
			if(!inLoad)
			{
				mMode.ID =txt1ID.Text;
				UpdateNode();
			}
		}

		private void chkDefault_CheckedChanged(object sender, System.EventArgs e)
		{
			if(!inLoad)
			{
				mMode.AllowAllActions   =chkDefault.Checked ;
				mMode.AllowAllActionsSpecified = true;
				UpdateNode();
			}
		}
	}
}
