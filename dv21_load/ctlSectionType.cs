using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Windows.Forms;
using dv21;
using dv21_util;
using dv21_ls;
using static System.Collections.Specialized.BitVector32;

namespace dv21_ctl
{
	/// <summary>
	/// Summary description for ctlSectionType.
	/// </summary>
	public class ctlSectionType : System.Windows.Forms.UserControl
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
		private System.Windows.Forms.Label label2;
		private System.Windows.Forms.ComboBox cmbType;
		private dv21.SectionType  mSection;
		private bool inLoad;
        private CheckBox cbHistory;
        private CheckBox cbWHO;
        private TextBox txtSequence;
        private Label label6;
        public MyTreeNode LastNode;

		private void UpdateNode()
		{
			LastNode.Text=  mSection.Name[0].Value +"(" + mSection.Name[0].Language + ")" ;
		}

		/// <summary> 
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		public ctlSectionType()
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
            this.label2 = new System.Windows.Forms.Label();
            this.cmbType = new System.Windows.Forms.ComboBox();
            this.cbHistory = new System.Windows.Forms.CheckBox();
            this.cbWHO = new System.Windows.Forms.CheckBox();
            this.txtSequence = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(204)));
            this.label1.Location = new System.Drawing.Point(0, 8);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(280, 24);
            this.label1.TabIndex = 0;
            this.label1.Text = "Раздел";
            // 
            // cmb1Names
            // 
            this.cmb1Names.Enabled = false;
            this.cmb1Names.Location = new System.Drawing.Point(3, 284);
            this.cmb1Names.Name = "cmb1Names";
            this.cmb1Names.Size = new System.Drawing.Size(224, 95);
            this.cmb1Names.TabIndex = 7;
            this.cmb1Names.SelectedIndexChanged += new System.EventHandler(this.cmb1Names_SelectedIndexChanged);
            // 
            // label5
            // 
            this.label5.Location = new System.Drawing.Point(3, 268);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(80, 16);
            this.label5.TabIndex = 29;
            this.label5.Text = "Названия";
            // 
            // cmd1Names
            // 
            this.cmd1Names.Location = new System.Drawing.Point(235, 284);
            this.cmd1Names.Name = "cmd1Names";
            this.cmd1Names.Size = new System.Drawing.Size(40, 24);
            this.cmd1Names.TabIndex = 8;
            this.cmd1Names.Text = "...";
            this.cmd1Names.Click += new System.EventHandler(this.cmd1Names_Click);
            // 
            // cmd1NewID
            // 
            this.cmd1NewID.Location = new System.Drawing.Point(240, 97);
            this.cmd1NewID.Name = "cmd1NewID";
            this.cmd1NewID.Size = new System.Drawing.Size(40, 24);
            this.cmd1NewID.TabIndex = 4;
            this.cmd1NewID.Text = "new";
            this.cmd1NewID.Click += new System.EventHandler(this.cmd1NewID_Click);
            // 
            // txt1ID
            // 
            this.txt1ID.Location = new System.Drawing.Point(8, 97);
            this.txt1ID.Name = "txt1ID";
            this.txt1ID.Size = new System.Drawing.Size(224, 20);
            this.txt1ID.TabIndex = 3;
            this.txt1ID.TextChanged += new System.EventHandler(this.txt1ID_TextChanged);
            // 
            // txt1Alias
            // 
            this.txt1Alias.Location = new System.Drawing.Point(8, 57);
            this.txt1Alias.Name = "txt1Alias";
            this.txt1Alias.Size = new System.Drawing.Size(272, 20);
            this.txt1Alias.TabIndex = 1;
            this.txt1Alias.TextChanged += new System.EventHandler(this.txt1Alias_TextChanged);
            // 
            // label4
            // 
            this.label4.Location = new System.Drawing.Point(8, 81);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(88, 16);
            this.label4.TabIndex = 2;
            this.label4.Text = "Идентификатор";
            // 
            // label3
            // 
            this.label3.Location = new System.Drawing.Point(8, 41);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(96, 16);
            this.label3.TabIndex = 0;
            this.label3.Text = "Псевдоним";
            // 
            // label2
            // 
            this.label2.Location = new System.Drawing.Point(8, 120);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(88, 16);
            this.label2.TabIndex = 5;
            this.label2.Text = "Тип";
            // 
            // cmbType
            // 
            this.cmbType.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cmbType.Items.AddRange(new object[] {
            "struct",
            "coll",
            "tree"});
            this.cmbType.Location = new System.Drawing.Point(8, 144);
            this.cmbType.Name = "cmbType";
            this.cmbType.Size = new System.Drawing.Size(272, 21);
            this.cmbType.TabIndex = 6;
            this.cmbType.SelectedIndexChanged += new System.EventHandler(this.cmbType_SelectedIndexChanged);
            // 
            // cbHistory
            // 
            this.cbHistory.AutoSize = true;
            this.cbHistory.Location = new System.Drawing.Point(6, 219);
            this.cbHistory.Name = "cbHistory";
            this.cbHistory.Size = new System.Drawing.Size(125, 17);
            this.cbHistory.TabIndex = 30;
            this.cbHistory.Text = "Исторические поля";
            this.cbHistory.UseVisualStyleBackColor = true;
            this.cbHistory.CheckedChanged += new System.EventHandler(this.cbHistory_CheckedChanged);
            // 
            // cbWHO
            // 
            this.cbWHO.AutoSize = true;
            this.cbWHO.Location = new System.Drawing.Point(6, 242);
            this.cbWHO.Name = "cbWHO";
            this.cbWHO.Size = new System.Drawing.Size(80, 17);
            this.cbWHO.TabIndex = 31;
            this.cbWHO.Text = "WHO поля";
            this.cbWHO.UseVisualStyleBackColor = true;
            this.cbWHO.CheckedChanged += new System.EventHandler(this.cbWHO_CheckedChanged);
            // 
            // txtSequence
            // 
            this.txtSequence.Location = new System.Drawing.Point(8, 193);
            this.txtSequence.Name = "txtSequence";
            this.txtSequence.Size = new System.Drawing.Size(272, 20);
            this.txtSequence.TabIndex = 33;
            this.txtSequence.TextChanged += new System.EventHandler(this.txtSequence_TextChanged);
            // 
            // label6
            // 
            this.label6.Location = new System.Drawing.Point(8, 177);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(96, 16);
            this.label6.TabIndex = 32;
            this.label6.Text = "Порядок вывода";
            // 
            // ctlSectionType
            // 
            this.Controls.Add(this.txtSequence);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.cbWHO);
            this.Controls.Add(this.cbHistory);
            this.Controls.Add(this.cmbType);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.cmb1Names);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.cmd1Names);
            this.Controls.Add(this.cmd1NewID);
            this.Controls.Add(this.txt1ID);
            this.Controls.Add(this.txt1Alias);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label1);
            this.Name = "ctlSectionType";
            this.Size = new System.Drawing.Size(288, 401);
            this.Load += new System.EventHandler(this.ctlSectionType_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

		}
		#endregion

		private void ctlSectionType_Load(object sender, System.EventArgs e)
		{
				inLoad = false;
		}

		private void cmd1NewID_Click(object sender, System.EventArgs e)
		{
			txt1ID.Text = System.Guid.NewGuid().ToString();
		}

		private void cmd1Names_Click(object sender, System.EventArgs e)
		{
			if (mSection.Name!=null)
			{
				LStringEditor f = new LStringEditor();
				f.LString=	mSection.Name ;
				f.InitList();
				f.ShowDialog();
				mSection.Name = f.LString; 
				int i;
				cmb1Names.Items.Clear();
				dv21.LocalizedStringsLocalizedString ls;
				for(i=0;i<mSection.Name.Length  ;i++)
				{
					ls=(dv21.LocalizedStringsLocalizedString) (mSection.Name[i]);
					cmb1Names.Items.Add(ls.Value +"(" +ls.Language  +")" );
				}
				UpdateNode();
			}
		}

		private void txt1ID_TextChanged(object sender, System.EventArgs e)
		{
			if(!inLoad)
			{
				mSection.ID =txt1ID.Text;
				UpdateNode();
			}
		}

		private void txt1Alias_TextChanged(object sender, System.EventArgs e)
		{
			if(!inLoad)
			{
				mSection.Alias  =txt1Alias.Text;
				UpdateNode();
			}
		}

		private void cmbType_SelectedIndexChanged(object sender, System.EventArgs e)
		{
			if(!inLoad)
			{
				mSection.Type =(dv21.SectionTypeType)  cmbType.SelectedIndex ;
				UpdateNode();
			}
		}

		public SectionType Section
		{
			get
			{
				return mSection;
			}
			set 
			{
				inLoad = true;
				mSection=value;
				if (mSection!=null)
				{
					txt1Alias.Text=mSection.Alias;
					txt1ID.Text=mSection.ID;
					cmbType.Text=mSection.Type.ToString();
                    cbHistory.Checked = mSection.AddHistory;
                    cbWHO.Checked = mSection.AddWhoInfo;
                    txtSequence.Text = mSection.Sequnce.ToString();

                    int i;
					if (mSection.Name!=null)
					{	
						cmb1Names.Items.Clear();
						for(i=0;i<mSection.Name.Length  ;i++)
						{
							cmb1Names.Items.Add(mSection.Name[i].Value +"(" +mSection.Name[i].Language  +")" );
						}
					}
				}
				inLoad = false;
			}
		}

        private void cbHistory_CheckedChanged(object sender, EventArgs e)
        {
            if (!inLoad)
            {
                mSection.AddHistory = cbHistory.Checked;
                UpdateNode();
            }
        }

        private void cbWHO_CheckedChanged(object sender, EventArgs e)
        {
            if (!inLoad)
            {
                mSection.AddWhoInfo = cbWHO.Checked;
                UpdateNode();
            }
        }

        private void cmb1Names_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void txtSequence_TextChanged(object sender, EventArgs e)
        {
            if (!inLoad)
            {
                try
                {
                    mSection.SequnceSpecified = true;
                    mSection.Sequnce = System.Convert.ToInt16(txtSequence.Text, 10);
                }
                catch
                {
                    mSection.SequnceSpecified = false;
                    mSection.Sequnce = 0;
                }
                
                UpdateNode();
            }
        }
    }
}
