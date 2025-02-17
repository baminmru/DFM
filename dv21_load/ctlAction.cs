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
	/// Summary description for ctlAction.
	/// </summary>
	public class ctlAction : System.Windows.Forms.UserControl
	{
		private System.Windows.Forms.Label label1;
		private System.Windows.Forms.ListBox cmb1Names;
		private System.Windows.Forms.Button cmd1Names;
		private System.Windows.Forms.Button cmd1NewID;
		private System.Windows.Forms.TextBox txt1ID;
		private System.Windows.Forms.Label label4;
		private bool inLoad;
		private ActionType  mAction;
		private System.Windows.Forms.Label label2;
		public MyTreeNode LastNode;

		private void UpdateNode(){
			LastNode.Text=mAction.Name[0].Value + "(" + mAction.Name[0].Language + ")";  
		}

		/// <summary> 
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		public ctlAction()
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
			this.cmd1Names = new System.Windows.Forms.Button();
			this.cmd1NewID = new System.Windows.Forms.Button();
			this.txt1ID = new System.Windows.Forms.TextBox();
			this.label4 = new System.Windows.Forms.Label();
			this.label2 = new System.Windows.Forms.Label();
			this.SuspendLayout();
			// 
			// label1
			// 
			this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((System.Byte)(204)));
			this.label1.Name = "label1";
			this.label1.Size = new System.Drawing.Size(144, 24);
			this.label1.TabIndex = 0;
			this.label1.Text = "Операция";
			// 
			// cmb1Names
			// 
			this.cmb1Names.Enabled = false;
			this.cmb1Names.Location = new System.Drawing.Point(4, 93);
			this.cmb1Names.Name = "cmb1Names";
			this.cmb1Names.Size = new System.Drawing.Size(224, 95);
			this.cmb1Names.TabIndex = 3;
			// 
			// cmd1Names
			// 
			this.cmd1Names.Location = new System.Drawing.Point(236, 93);
			this.cmd1Names.Name = "cmd1Names";
			this.cmd1Names.Size = new System.Drawing.Size(40, 24);
			this.cmd1Names.TabIndex = 4;
			this.cmd1Names.Text = "...";
			this.cmd1Names.Click += new System.EventHandler(this.cmd1Names_Click);
			// 
			// cmd1NewID
			// 
			this.cmd1NewID.Location = new System.Drawing.Point(236, 45);
			this.cmd1NewID.Name = "cmd1NewID";
			this.cmd1NewID.Size = new System.Drawing.Size(40, 24);
			this.cmd1NewID.TabIndex = 2;
			this.cmd1NewID.Text = "new";
			this.cmd1NewID.Click += new System.EventHandler(this.cmd1NewID_Click);
			// 
			// txt1ID
			// 
			this.txt1ID.Location = new System.Drawing.Point(4, 45);
			this.txt1ID.Name = "txt1ID";
			this.txt1ID.Size = new System.Drawing.Size(224, 20);
			this.txt1ID.TabIndex = 1;
			this.txt1ID.Text = "";
			this.txt1ID.TextChanged += new System.EventHandler(this.txt1ID_TextChanged);
			// 
			// label4
			// 
			this.label4.Location = new System.Drawing.Point(4, 29);
			this.label4.Name = "label4";
			this.label4.Size = new System.Drawing.Size(88, 16);
			this.label4.TabIndex = 0;
			this.label4.Text = "Идентификатор";
			// 
			// label2
			// 
			this.label2.BackColor = System.Drawing.SystemColors.Menu;
			this.label2.Location = new System.Drawing.Point(8, 72);
			this.label2.Name = "label2";
			this.label2.Size = new System.Drawing.Size(152, 16);
			this.label2.TabIndex = 5;
			this.label2.Text = "Названия";
			// 
			// ctlAction
			// 
			this.Controls.AddRange(new System.Windows.Forms.Control[] {
																		  this.label2,
																		  this.cmb1Names,
																		  this.cmd1Names,
																		  this.cmd1NewID,
																		  this.txt1ID,
																		  this.label4,
																		  this.label1});
			this.Name = "ctlAction";
			this.Size = new System.Drawing.Size(280, 216);
			this.Load += new System.EventHandler(this.ctlAction_Load);
			this.ResumeLayout(false);

		}
		#endregion

		private void ctlActionType_Load(object sender, System.EventArgs e)
		{
			inLoad = false;
		}

		public ActionType Action
		{
			get
			{
				return mAction;
			}
			set
			{
				mAction = value;
				if(mAction!=null)
				{
					inLoad = true;

					txt1ID.Text = mAction.ID;
					cmb1Names.Items.Clear(); 
					int i;
					if (mAction.Name!=null)
					{	
						for(i=0;i<mAction.Name.Length  ;i++)
						{
							cmb1Names.Items.Add(mAction.Name[i].Value +"(" +mAction.Name[i].Language  +")" );
						}
					}
					inLoad = false;
				}
			}
		}


		private void cmd1NewID_Click(object sender, System.EventArgs e)
		{
			txt1ID.Text = System.Guid.NewGuid().ToString();
			UpdateNode();
		}

		private void cmd1Names_Click(object sender, System.EventArgs e)
		{
			if (mAction.Name!=null)
			{
				LStringEditor f = new LStringEditor();
				f.LString=	mAction.Name ;
				f.InitList();
				f.ShowDialog();
				mAction.Name = f.LString; 
				int i;
				cmb1Names.Items.Clear();
				dv21.LocalizedStringsLocalizedString ls;
				for(i=0;i<mAction.Name.Length  ;i++)
				{
					ls=(dv21.LocalizedStringsLocalizedString) (mAction.Name[i]);
					cmb1Names.Items.Add(ls.Value +"(" +ls.Language  +")" );
				}
				UpdateNode();			
			}
		}

		private void txt1ID_TextChanged(object sender, System.EventArgs e)
		{
			if(!inLoad)
			{
				mAction.ID =txt1ID.Text;
				UpdateNode();
			}
		}

		

		private void ctlAction_Load(object sender, System.EventArgs e)
		{
			inLoad = false;		
		}

	}
}
