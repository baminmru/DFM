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
	/// Summary description for UserControl1.
	/// </summary>
	public class ctlCardDefinition : System.Windows.Forms.UserControl
	{
		private System.Windows.Forms.Label label5;
		private System.Windows.Forms.Button cmd1Names;
		private System.Windows.Forms.Button cmd1NewID;
		private System.Windows.Forms.TextBox txt1ID;
		private System.Windows.Forms.TextBox txt1Alias;
		private System.Windows.Forms.TextBox txt1version;
		private System.Windows.Forms.Label label4;
		private System.Windows.Forms.Label label3;
		private System.Windows.Forms.Label label2;
		private System.Windows.Forms.Label label1;
		private dv21.CardDefinition mcd;
		private System.Windows.Forms.ListBox cmb1Names;
		private bool inLoad ;
        public MyTreeNode LastNode;
        private CheckBox cbSingleTone;
        private TextBox txtSchema;
        private Label label6;

        public dv21.CardDefinition cd 
		{
			get	{
				return mcd;
			}

			set
			{
				mcd=value;

				if (cd != null)
				{
					inLoad = true;
					txt1Alias.Text =mcd.Alias ;
					txt1version.Text=mcd.Version.ToString() ;
					txt1ID.Text = mcd.ID;
                    cbSingleTone.Checked = mcd.SingleTone;
                    txtSchema.Text = cd.Schema;
                    cmb1Names.Items.Clear(); 
					int i;
					if (mcd.Name!=null)
					{	
						for(i=0;i<mcd.Name.Length  ;i++)
						{
							cmb1Names.Items.Add(mcd.Name[i].Value +"(" +mcd.Name[i].Language  +")" );
						}
					}
					inLoad = false;
				}
			}
		}

		/// <summary> 
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		public ctlCardDefinition()
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
            this.label5 = new System.Windows.Forms.Label();
            this.cmd1Names = new System.Windows.Forms.Button();
            this.cmd1NewID = new System.Windows.Forms.Button();
            this.txt1ID = new System.Windows.Forms.TextBox();
            this.txt1Alias = new System.Windows.Forms.TextBox();
            this.txt1version = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.cmb1Names = new System.Windows.Forms.ListBox();
            this.cbSingleTone = new System.Windows.Forms.CheckBox();
            this.txtSchema = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // label5
            // 
            this.label5.Location = new System.Drawing.Point(16, 262);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(80, 16);
            this.label5.TabIndex = 7;
            this.label5.Text = "Названия";
            // 
            // cmd1Names
            // 
            this.cmd1Names.Location = new System.Drawing.Point(248, 278);
            this.cmd1Names.Name = "cmd1Names";
            this.cmd1Names.Size = new System.Drawing.Size(40, 24);
            this.cmd1Names.TabIndex = 9;
            this.cmd1Names.Text = "...";
            this.cmd1Names.Click += new System.EventHandler(this.cmd1Names_Click);
            // 
            // cmd1NewID
            // 
            this.cmd1NewID.Location = new System.Drawing.Point(248, 186);
            this.cmd1NewID.Name = "cmd1NewID";
            this.cmd1NewID.Size = new System.Drawing.Size(40, 24);
            this.cmd1NewID.TabIndex = 6;
            this.cmd1NewID.Text = "new";
            this.cmd1NewID.Click += new System.EventHandler(this.cmd1NewID_Click);
            // 
            // txt1ID
            // 
            this.txt1ID.Location = new System.Drawing.Point(16, 186);
            this.txt1ID.Name = "txt1ID";
            this.txt1ID.Size = new System.Drawing.Size(224, 20);
            this.txt1ID.TabIndex = 5;
            this.txt1ID.TextChanged += new System.EventHandler(this.txt1ID_TextChanged);
            // 
            // txt1Alias
            // 
            this.txt1Alias.Location = new System.Drawing.Point(16, 88);
            this.txt1Alias.Name = "txt1Alias";
            this.txt1Alias.Size = new System.Drawing.Size(272, 20);
            this.txt1Alias.TabIndex = 3;
            this.txt1Alias.TextChanged += new System.EventHandler(this.txt1Alias_TextChanged);
            // 
            // txt1version
            // 
            this.txt1version.Location = new System.Drawing.Point(16, 48);
            this.txt1version.Name = "txt1version";
            this.txt1version.Size = new System.Drawing.Size(272, 20);
            this.txt1version.TabIndex = 1;
            this.txt1version.TextChanged += new System.EventHandler(this.txt1version_TextChanged);
            // 
            // label4
            // 
            this.label4.Location = new System.Drawing.Point(16, 170);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(88, 16);
            this.label4.TabIndex = 4;
            this.label4.Text = "Идентификатор";
            // 
            // label3
            // 
            this.label3.Location = new System.Drawing.Point(16, 72);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(96, 16);
            this.label3.TabIndex = 2;
            this.label3.Text = "Псевдоним";
            // 
            // label2
            // 
            this.label2.Location = new System.Drawing.Point(16, 32);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(96, 16);
            this.label2.TabIndex = 0;
            this.label2.Text = "Версия";
            // 
            // label1
            // 
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(204)));
            this.label1.Location = new System.Drawing.Point(0, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(272, 16);
            this.label1.TabIndex = 11;
            this.label1.Text = "Общее описание";
            // 
            // cmb1Names
            // 
            this.cmb1Names.Enabled = false;
            this.cmb1Names.Location = new System.Drawing.Point(16, 278);
            this.cmb1Names.Name = "cmb1Names";
            this.cmb1Names.Size = new System.Drawing.Size(224, 95);
            this.cmb1Names.TabIndex = 8;
            // 
            // cbSingleTone
            // 
            this.cbSingleTone.AutoSize = true;
            this.cbSingleTone.Location = new System.Drawing.Point(16, 227);
            this.cbSingleTone.Name = "cbSingleTone";
            this.cbSingleTone.Size = new System.Drawing.Size(139, 17);
            this.cbSingleTone.TabIndex = 12;
            this.cbSingleTone.Text = "Единственный объект";
            this.cbSingleTone.UseVisualStyleBackColor = true;
            this.cbSingleTone.CheckedChanged += new System.EventHandler(this.cbSingleTone_CheckedChanged);
            // 
            // txtSchema
            // 
            this.txtSchema.Location = new System.Drawing.Point(13, 136);
            this.txtSchema.Name = "txtSchema";
            this.txtSchema.Size = new System.Drawing.Size(272, 20);
            this.txtSchema.TabIndex = 14;
            this.txtSchema.TextChanged += new System.EventHandler(this.txtScheme_TextChanged);
            // 
            // label6
            // 
            this.label6.Location = new System.Drawing.Point(13, 120);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(96, 16);
            this.label6.TabIndex = 13;
            this.label6.Text = "Схема";
            // 
            // ctlCardDefinition
            // 
            this.Controls.Add(this.txtSchema);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.cbSingleTone);
            this.Controls.Add(this.cmb1Names);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.cmd1Names);
            this.Controls.Add(this.cmd1NewID);
            this.Controls.Add(this.txt1ID);
            this.Controls.Add(this.txt1Alias);
            this.Controls.Add(this.txt1version);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Name = "ctlCardDefinition";
            this.Size = new System.Drawing.Size(304, 392);
            this.Load += new System.EventHandler(this.ctlCardDefinition_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

		}
		#endregion

		private void ctlCardDefinition_Load(object sender, System.EventArgs e)
		{
			inLoad = false;
		}



		private void cmd1NewID_Click(object sender, System.EventArgs e)
		{
			txt1ID.Text = System.Guid.NewGuid().ToString();

		}

		private void cmd1Names_Click(object sender, System.EventArgs e)
		{
			
			if (cd.Name!=null)
			{
				LStringEditor f = new LStringEditor();
				f.LString=	mcd.Name ;
				f.InitList();
				f.ShowDialog();
				mcd.Name = f.LString; 
				int i;
				cmb1Names.Items.Clear();
				dv21.LocalizedStringsLocalizedString ls;
				for(i=0;i<cd.Name.Length  ;i++)
				{
					ls=(dv21.LocalizedStringsLocalizedString) (mcd.Name[i]);
					cmb1Names.Items.Add(ls.Value +"(" +ls.Language  +")" );
				}
			
			}
						
		}

		private void txt1version_TextChanged(object sender, System.EventArgs e)
		{
			if(!inLoad)
			{
					cd.Version =int.Parse( txt1version.Text);
			}
		}

		private void txt1Alias_TextChanged(object sender, System.EventArgs e)
		{
			if(!inLoad)
			{
				cd.Alias =txt1Alias.Text;
			}
		}

		private void txt1ID_TextChanged(object sender, System.EventArgs e)
		{
			if(!inLoad)
			{
				cd.ID =txt1ID.Text;
			}
		}

        private void cbSingleTone_CheckedChanged(object sender, EventArgs e)
        {

            if (!inLoad)
            {
                mcd.SingleTone = cbSingleTone.Checked;
            }

        }

        private void txtScheme_TextChanged(object sender, EventArgs e)
        {
            if (!inLoad)
            {
                cd.Schema = txtSchema.Text;
            }
        }
    }
}
