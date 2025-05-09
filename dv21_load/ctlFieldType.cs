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
	/// Summary description for ctlFieldType.
	/// </summary>
	public class ctlFieldType : System.Windows.Forms.UserControl
	{
		private System.Windows.Forms.Label label1;
		private System.Windows.Forms.ComboBox cmbType;
		private System.Windows.Forms.Label label2;
		private System.Windows.Forms.ListBox cmb1Names;
		private System.Windows.Forms.Label label5;
		private System.Windows.Forms.Button cmd1Names;
		private System.Windows.Forms.Button cmd1NewID;
		private System.Windows.Forms.TextBox txt1ID;
		private System.Windows.Forms.TextBox txt1Alias;
		private System.Windows.Forms.Label label4;
		private System.Windows.Forms.Label label3;
		private System.Windows.Forms.Label label6;
		private System.Windows.Forms.TextBox txtMax;
		private System.Windows.Forms.Label label7;
		private System.Windows.Forms.CheckBox chkNotNull;
		private bool inLoad;
		private FieldType mField;
		private System.Windows.Forms.CheckBox chkReference;
		private System.Windows.Forms.Label label8;
		private System.Windows.Forms.Label label9;
		private System.Windows.Forms.TextBox txtRefType;
		private System.Windows.Forms.TextBox txtRefSection;
		private System.Windows.Forms.Button cmdStructID;
		private System.Windows.Forms.Button cmdRefType;
        private Label label11;
        private CheckBox chkLookup;
        private TextBox txtLookup;
        private Label label12;
        private GroupBox groupBox1;
        private GroupBox groupBox2;
        private Label label10;
        private CheckBox chkIsBrief;
        private Label label13;
        private CheckBox chkUseForPK;
        private Label label14;
        private TextBox txtRefName;
        private Label label15;
        private TextBox txtEnumName;
        private TextBox txtDocumentation;
        private Label label16;
        public MyTreeNode LastNode;

		private void UpdateNode()
		{
			LastNode.Text=mField.Name[0].Value + "(" + mField.Name[0].Language + ")" ;
            frmCard f = (frmCard)this.ParentForm;
            f.Saved = false;
		}

		/// <summary> 
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		public ctlFieldType()
		{
			// This call is required by the Windows.Forms Form Designer.
			InitializeComponent();

			// TODO: Add any initialization after the InitForm call

		}


		public FieldType Field{
			get
			{
				return mField;
			}
			set
			{
				mField = value;
				if (mField != null)
				{
					inLoad = true;
					txt1Alias.Text =mField.Alias;
					txt1ID.Text = mField.ID;
					
					if (mField.MaxSpecified)
						txtMax.Text = mField.Max.ToString() ;
					else
						txtMax.Text = "";

					chkNotNull.Checked =mField.NotNull;
                    chkIsBrief.Checked = mField.IsBrief;

                    chkUseForPK.Checked =mField.UseforPK;

                    cmbType.Text = mField.Type.ToString();

                    chkLookup.Checked = mField.Lookup;
                    txtLookup.Text = mField.LookupExpression;
                    txtDocumentation.Text = mField.Documentation;



                    if (mField.Type == FieldTypeType.@enum)
                    {
                        txtEnumName.ReadOnly = false;
                        txtEnumName.Text = mField.EnumName;
                    }
                    else
                    {
                        txtEnumName.ReadOnly = true;
                        txtEnumName.Text = "";
                    }
                    


                    chkReference.Checked = mField.Reference;
                    if (chkReference.Checked)
                    {
                        txtRefType.Text = mField.RefType;
                        txtRefSection.Text = mField.RefSection;
                        UpdateRefName();

                    }
                    else
                    {
                        txtRefType.Text = "";
                        txtRefSection.Text = "";
                        txtRefName.Text = "";
                    }

                        int i;
					cmb1Names.Items.Clear();
					dv21.LocalizedStringsLocalizedString ls;
					for(i=0;i<mField.Name.Length  ;i++)
					{
						ls=(dv21.LocalizedStringsLocalizedString) (mField.Name[i]);
						cmb1Names.Items.Add(ls.Value +"(" +ls.Language  +")" );
					}
					inLoad = false;
 
				}
				
			}
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
            this.cmbType = new System.Windows.Forms.ComboBox();
            this.label2 = new System.Windows.Forms.Label();
            this.cmb1Names = new System.Windows.Forms.ListBox();
            this.label5 = new System.Windows.Forms.Label();
            this.cmd1Names = new System.Windows.Forms.Button();
            this.cmd1NewID = new System.Windows.Forms.Button();
            this.txt1ID = new System.Windows.Forms.TextBox();
            this.txt1Alias = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.txtMax = new System.Windows.Forms.TextBox();
            this.label7 = new System.Windows.Forms.Label();
            this.chkNotNull = new System.Windows.Forms.CheckBox();
            this.chkReference = new System.Windows.Forms.CheckBox();
            this.label8 = new System.Windows.Forms.Label();
            this.label9 = new System.Windows.Forms.Label();
            this.txtRefType = new System.Windows.Forms.TextBox();
            this.txtRefSection = new System.Windows.Forms.TextBox();
            this.cmdStructID = new System.Windows.Forms.Button();
            this.cmdRefType = new System.Windows.Forms.Button();
            this.label11 = new System.Windows.Forms.Label();
            this.chkLookup = new System.Windows.Forms.CheckBox();
            this.txtLookup = new System.Windows.Forms.TextBox();
            this.label12 = new System.Windows.Forms.Label();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.txtRefName = new System.Windows.Forms.TextBox();
            this.label10 = new System.Windows.Forms.Label();
            this.chkIsBrief = new System.Windows.Forms.CheckBox();
            this.label13 = new System.Windows.Forms.Label();
            this.chkUseForPK = new System.Windows.Forms.CheckBox();
            this.label14 = new System.Windows.Forms.Label();
            this.label15 = new System.Windows.Forms.Label();
            this.txtEnumName = new System.Windows.Forms.TextBox();
            this.txtDocumentation = new System.Windows.Forms.TextBox();
            this.label16 = new System.Windows.Forms.Label();
            this.groupBox1.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(204)));
            this.label1.Location = new System.Drawing.Point(8, 8);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(136, 24);
            this.label1.TabIndex = 0;
            this.label1.Text = "Поле";
            // 
            // cmbType
            // 
            this.cmbType.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cmbType.Items.AddRange(new object[] {
            "int",
            "int8",
            "bool",
            "datetime",
            "enum",
            "bitmask",
            "uniqueid",
            "userid",
            "string",
            "unistring",
            "fileid",
            "float",
            "double",
            "refid",
            "text",
            "image",
            "json",
            "timestamp",
            "timestamptz"});
            this.cmbType.Location = new System.Drawing.Point(8, 128);
            this.cmbType.Name = "cmbType";
            this.cmbType.Size = new System.Drawing.Size(136, 28);
            this.cmbType.TabIndex = 6;
            this.cmbType.SelectedIndexChanged += new System.EventHandler(this.cmbType_SelectedIndexChanged);
            // 
            // label2
            // 
            this.label2.Location = new System.Drawing.Point(8, 104);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(88, 16);
            this.label2.TabIndex = 5;
            this.label2.Text = "Тип хранения";
            // 
            // cmb1Names
            // 
            this.cmb1Names.Enabled = false;
            this.cmb1Names.ItemHeight = 20;
            this.cmb1Names.Location = new System.Drawing.Point(8, 596);
            this.cmb1Names.Name = "cmb1Names";
            this.cmb1Names.Size = new System.Drawing.Size(285, 44);
            this.cmb1Names.TabIndex = 12;
            // 
            // label5
            // 
            this.label5.Location = new System.Drawing.Point(9, 577);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(134, 16);
            this.label5.TabIndex = 26;
            this.label5.Text = "Названия";
            this.label5.Click += new System.EventHandler(this.label5_Click);
            // 
            // cmd1Names
            // 
            this.cmd1Names.Location = new System.Drawing.Point(199, 566);
            this.cmd1Names.Name = "cmd1Names";
            this.cmd1Names.Size = new System.Drawing.Size(94, 24);
            this.cmd1Names.TabIndex = 28;
            this.cmd1Names.Text = "...";
            this.cmd1Names.Click += new System.EventHandler(this.cmd1Names_Click);
            // 
            // cmd1NewID
            // 
            this.cmd1NewID.Location = new System.Drawing.Point(240, 80);
            this.cmd1NewID.Name = "cmd1NewID";
            this.cmd1NewID.Size = new System.Drawing.Size(40, 24);
            this.cmd1NewID.TabIndex = 4;
            this.cmd1NewID.Text = "new";
            this.cmd1NewID.Click += new System.EventHandler(this.cmd1NewID_Click);
            // 
            // txt1ID
            // 
            this.txt1ID.Location = new System.Drawing.Point(8, 80);
            this.txt1ID.Name = "txt1ID";
            this.txt1ID.Size = new System.Drawing.Size(224, 26);
            this.txt1ID.TabIndex = 3;
            this.txt1ID.TextChanged += new System.EventHandler(this.txt1ID_TextChanged);
            // 
            // txt1Alias
            // 
            this.txt1Alias.Location = new System.Drawing.Point(6, 41);
            this.txt1Alias.Name = "txt1Alias";
            this.txt1Alias.Size = new System.Drawing.Size(272, 26);
            this.txt1Alias.TabIndex = 1;
            this.txt1Alias.TextChanged += new System.EventHandler(this.txt1Alias_TextChanged);
            // 
            // label4
            // 
            this.label4.Location = new System.Drawing.Point(8, 64);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(88, 16);
            this.label4.TabIndex = 2;
            this.label4.Text = "Идентификатор";
            // 
            // label3
            // 
            this.label3.Location = new System.Drawing.Point(8, 24);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(96, 16);
            this.label3.TabIndex = 0;
            this.label3.Text = "Псевдоним";
            // 
            // label6
            // 
            this.label6.Location = new System.Drawing.Point(8, 152);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(112, 16);
            this.label6.TabIndex = 7;
            this.label6.Text = "Размер";
            // 
            // txtMax
            // 
            this.txtMax.Location = new System.Drawing.Point(8, 168);
            this.txtMax.Name = "txtMax";
            this.txtMax.Size = new System.Drawing.Size(272, 26);
            this.txtMax.TabIndex = 8;
            this.txtMax.TextChanged += new System.EventHandler(this.txtMax_TextChanged);
            // 
            // label7
            // 
            this.label7.Location = new System.Drawing.Point(8, 192);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(96, 16);
            this.label7.TabIndex = 9;
            this.label7.Text = "Обязательное";
            // 
            // chkNotNull
            // 
            this.chkNotNull.Location = new System.Drawing.Point(210, 198);
            this.chkNotNull.Name = "chkNotNull";
            this.chkNotNull.Size = new System.Drawing.Size(24, 16);
            this.chkNotNull.TabIndex = 10;
            this.chkNotNull.CheckedChanged += new System.EventHandler(this.chkNotNull_CheckedChanged);
            // 
            // chkReference
            // 
            this.chkReference.Location = new System.Drawing.Point(200, 19);
            this.chkReference.Name = "chkReference";
            this.chkReference.Size = new System.Drawing.Size(24, 16);
            this.chkReference.TabIndex = 15;
            this.chkReference.CheckedChanged += new System.EventHandler(this.chkReference_CheckedChanged);
            // 
            // label8
            // 
            this.label8.Location = new System.Drawing.Point(10, 20);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(96, 16);
            this.label8.TabIndex = 14;
            this.label8.Text = "Ссылка";
            // 
            // label9
            // 
            this.label9.Location = new System.Drawing.Point(7, 46);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(272, 24);
            this.label9.TabIndex = 16;
            this.label9.Text = "Тип, куда ссылаемся (ID)";
            // 
            // txtRefType
            // 
            this.txtRefType.Location = new System.Drawing.Point(7, 62);
            this.txtRefType.Name = "txtRefType";
            this.txtRefType.Size = new System.Drawing.Size(232, 26);
            this.txtRefType.TabIndex = 17;
            this.txtRefType.TextChanged += new System.EventHandler(this.txtRefType_TextChanged);
            // 
            // txtRefSection
            // 
            this.txtRefSection.Location = new System.Drawing.Point(7, 110);
            this.txtRefSection.Name = "txtRefSection";
            this.txtRefSection.Size = new System.Drawing.Size(232, 26);
            this.txtRefSection.TabIndex = 20;
            this.txtRefSection.TextChanged += new System.EventHandler(this.txtRefSection_TextChanged);
            // 
            // cmdStructID
            // 
            this.cmdStructID.Location = new System.Drawing.Point(247, 110);
            this.cmdStructID.Name = "cmdStructID";
            this.cmdStructID.Size = new System.Drawing.Size(32, 24);
            this.cmdStructID.TabIndex = 21;
            this.cmdStructID.Text = "...";
            this.cmdStructID.Click += new System.EventHandler(this.cmdStructID_Click);
            // 
            // cmdRefType
            // 
            this.cmdRefType.Location = new System.Drawing.Point(247, 62);
            this.cmdRefType.Name = "cmdRefType";
            this.cmdRefType.Size = new System.Drawing.Size(32, 24);
            this.cmdRefType.TabIndex = 18;
            this.cmdRefType.Text = "...";
            this.cmdRefType.Click += new System.EventHandler(this.cmdRefType_Click);
            // 
            // label11
            // 
            this.label11.Location = new System.Drawing.Point(13, 25);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(96, 16);
            this.label11.TabIndex = 23;
            this.label11.Text = "Lookup";
            // 
            // chkLookup
            // 
            this.chkLookup.Location = new System.Drawing.Point(200, 25);
            this.chkLookup.Name = "chkLookup";
            this.chkLookup.Size = new System.Drawing.Size(24, 16);
            this.chkLookup.TabIndex = 23;
            this.chkLookup.CheckedChanged += new System.EventHandler(this.checkBox1_CheckedChanged);
            // 
            // txtLookup
            // 
            this.txtLookup.Location = new System.Drawing.Point(9, 69);
            this.txtLookup.Name = "txtLookup";
            this.txtLookup.Size = new System.Drawing.Size(224, 26);
            this.txtLookup.TabIndex = 25;
            this.txtLookup.TextChanged += new System.EventHandler(this.txtLookup_TextChanged);
            // 
            // label12
            // 
            this.label12.Location = new System.Drawing.Point(6, 49);
            this.label12.Name = "label12";
            this.label12.Size = new System.Drawing.Size(190, 17);
            this.label12.TabIndex = 24;
            this.label12.Text = "Выражение для Lookup";
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.txtLookup);
            this.groupBox1.Controls.Add(this.label12);
            this.groupBox1.Controls.Add(this.chkLookup);
            this.groupBox1.Controls.Add(this.label11);
            this.groupBox1.Location = new System.Drawing.Point(8, 454);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(285, 106);
            this.groupBox1.TabIndex = 22;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Lookup";
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.txtRefName);
            this.groupBox2.Controls.Add(this.label10);
            this.groupBox2.Controls.Add(this.cmdRefType);
            this.groupBox2.Controls.Add(this.cmdStructID);
            this.groupBox2.Controls.Add(this.txtRefSection);
            this.groupBox2.Controls.Add(this.txtRefType);
            this.groupBox2.Controls.Add(this.label9);
            this.groupBox2.Controls.Add(this.chkReference);
            this.groupBox2.Controls.Add(this.label8);
            this.groupBox2.Location = new System.Drawing.Point(8, 272);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(287, 176);
            this.groupBox2.TabIndex = 15;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Ссылочное поле";
            // 
            // txtRefName
            // 
            this.txtRefName.Location = new System.Drawing.Point(6, 150);
            this.txtRefName.Name = "txtRefName";
            this.txtRefName.ReadOnly = true;
            this.txtRefName.Size = new System.Drawing.Size(267, 26);
            this.txtRefName.TabIndex = 22;
            // 
            // label10
            // 
            this.label10.Location = new System.Drawing.Point(7, 89);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(272, 16);
            this.label10.TabIndex = 19;
            this.label10.Text = "Раздел, куда ссылаемся (ID)";
            // 
            // chkIsBrief
            // 
            this.chkIsBrief.Location = new System.Drawing.Point(210, 220);
            this.chkIsBrief.Name = "chkIsBrief";
            this.chkIsBrief.Size = new System.Drawing.Size(24, 16);
            this.chkIsBrief.TabIndex = 12;
            this.chkIsBrief.CheckedChanged += new System.EventHandler(this.chkIsBrief_CheckedChanged);
            // 
            // label13
            // 
            this.label13.Location = new System.Drawing.Point(9, 221);
            this.label13.Name = "label13";
            this.label13.Size = new System.Drawing.Size(179, 15);
            this.label13.TabIndex = 11;
            this.label13.Text = "Краткое отображение";
            // 
            // chkUseForPK
            // 
            this.chkUseForPK.Location = new System.Drawing.Point(210, 250);
            this.chkUseForPK.Name = "chkUseForPK";
            this.chkUseForPK.Size = new System.Drawing.Size(24, 16);
            this.chkUseForPK.TabIndex = 14;
            this.chkUseForPK.CheckedChanged += new System.EventHandler(this.chkUseForPK_CheckedChanged);
            // 
            // label14
            // 
            this.label14.Location = new System.Drawing.Point(9, 251);
            this.label14.Name = "label14";
            this.label14.Size = new System.Drawing.Size(179, 15);
            this.label14.TabIndex = 13;
            this.label14.Text = "Использовать как ключ";
            // 
            // label15
            // 
            this.label15.AutoSize = true;
            this.label15.Location = new System.Drawing.Point(152, 104);
            this.label15.Name = "label15";
            this.label15.Size = new System.Drawing.Size(129, 20);
            this.label15.TabIndex = 29;
            this.label15.Text = "Название Enum";
            // 
            // txtEnumName
            // 
            this.txtEnumName.Location = new System.Drawing.Point(156, 128);
            this.txtEnumName.Name = "txtEnumName";
            this.txtEnumName.Size = new System.Drawing.Size(121, 26);
            this.txtEnumName.TabIndex = 30;
            this.txtEnumName.TextChanged += new System.EventHandler(this.txtEnumName_TextChanged);
            // 
            // txtDocumentation
            // 
            this.txtDocumentation.Location = new System.Drawing.Point(8, 682);
            this.txtDocumentation.Multiline = true;
            this.txtDocumentation.Name = "txtDocumentation";
            this.txtDocumentation.ScrollBars = System.Windows.Forms.ScrollBars.Both;
            this.txtDocumentation.Size = new System.Drawing.Size(284, 83);
            this.txtDocumentation.TabIndex = 31;
            this.txtDocumentation.TextChanged += new System.EventHandler(this.txtDocumentation_TextChanged);
            // 
            // label16
            // 
            this.label16.Location = new System.Drawing.Point(10, 663);
            this.label16.Name = "label16";
            this.label16.Size = new System.Drawing.Size(134, 16);
            this.label16.TabIndex = 32;
            this.label16.Text = "Комментарий";
            // 
            // ctlFieldType
            // 
            this.Controls.Add(this.label16);
            this.Controls.Add(this.txtDocumentation);
            this.Controls.Add(this.txtEnumName);
            this.Controls.Add(this.label15);
            this.Controls.Add(this.chkUseForPK);
            this.Controls.Add(this.label14);
            this.Controls.Add(this.chkIsBrief);
            this.Controls.Add(this.label13);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.chkNotNull);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.txtMax);
            this.Controls.Add(this.label6);
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
            this.Controls.Add(this.groupBox1);
            this.Name = "ctlFieldType";
            this.Size = new System.Drawing.Size(358, 784);
            this.Load += new System.EventHandler(this.ctlFieldType_Load);
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

		}
		#endregion

		private void cmd1NewID_Click(object sender, System.EventArgs e)
		{
			txt1ID.Text = System.Guid.NewGuid().ToString();
		}

		private void ctlFieldType_Load(object sender, System.EventArgs e)
		{
		
		}

		private void cmbType_SelectedIndexChanged(object sender, System.EventArgs e)
		{
			if(!inLoad)
			{
				mField.Type =(dv21.FieldTypeType)  cmbType.SelectedIndex ;
                if (mField.Type == FieldTypeType.@enum)
                {
                    txtEnumName.ReadOnly = false;
                }
                else
                {
                    txtEnumName.ReadOnly = true;
                }

                    UpdateNode();
			}
		}

		private void cmd1Names_Click(object sender, System.EventArgs e)
		{
			if (mField.Name!=null)
			{
				LStringEditor f = new LStringEditor();
				f.LString=	mField.Name ;
				f.InitList();
				f.ShowDialog();
				mField.Name = f.LString; 
				int i;
				cmb1Names.Items.Clear();
				dv21.LocalizedStringsLocalizedString ls;
				for(i=0;i<mField.Name.Length  ;i++)
				{
					ls=(dv21.LocalizedStringsLocalizedString) (mField.Name[i]);
					cmb1Names.Items.Add(ls.Value +"(" +ls.Language  +")" );
				}
				UpdateNode();
			}
		}

		private void txt1ID_TextChanged(object sender, System.EventArgs e)
		{
			if(!inLoad)
			{
				mField.ID =txt1ID.Text;
				UpdateNode();
			}
		}

		private void txt1Alias_TextChanged(object sender, System.EventArgs e)
		{
			if(!inLoad)
			{
				mField.Alias  =txt1Alias.Text;
				UpdateNode();
			}
		}

		private void txtMax_TextChanged(object sender, System.EventArgs e)
		{
			if(!inLoad)
			{
				try
				{
					
					mField.Max  =System.Convert.ToInt16(txtMax.Text,10);
					mField.MaxSpecified =true;
				}
				catch{
					txtMax.Text="";
					mField.Max  =0;
					mField.MaxSpecified =false;
				}
				UpdateNode();
			}
		}

		private void chkNotNull_CheckedChanged(object sender, System.EventArgs e)
		{
			if(!inLoad)
			{
				try
				{
					mField.NotNull   =chkNotNull.Checked;
					mField.NotNullSpecified =true;
				}
				catch{}
				UpdateNode();
			}
		}

		private void chkReference_CheckedChanged(object sender, System.EventArgs e)
		{
			if(!inLoad)
			{
				try
				{
					mField.Reference    = chkReference.Checked;
					mField.ReferenceSpecified = chkReference.Checked;
                }
				catch{}
				UpdateNode();
			}
		}

		private void txtRefType_TextChanged(object sender, System.EventArgs e)
		{
			if(!inLoad)
			{
				mField.RefType  =txtRefType.Text ; 
				UpdateNode();
			}
		}

		private void txtRefSection_TextChanged(object sender, System.EventArgs e)
		{
			if(!inLoad)
			{
				mField.RefSection  =txtRefSection.Text ; 
				UpdateNode();
			}

		}

		private void cmdStructID_Click(object sender, System.EventArgs e)
		{
			dv21_load.frmSectionView f = new dv21_load.frmSectionView();
			if(f.ShowDialog()==System.Windows.Forms.DialogResult.OK)
			{
				txtRefSection.Text = f.ID;
                UpdateRefName();
            }

		}

        private void UpdateRefName()
        {
            dv21.CardDefinition refT = MyUtils.GetReferencedType(MyUtils.cards, txtRefType.Text);
            dv21.SectionType refS = MyUtils.ResolveReference(MyUtils.cards, txtRefSection.Text);
            txtRefName.Text = "";
            if (refT != null)
                txtRefName.Text = refT.Alias;

            if (refS != null)
            {
                string idName = MyUtils.GetIDName(refS);
                txtRefName.Text += " :: " + refS.Alias +"." + idName;
            }
                
        }


		private void cmdRefType_Click(object sender, System.EventArgs e)
		{
			dv21_load.frmSectionView f = new dv21_load.frmSectionView();
			f.TypeOnly= true;
			if(f.ShowDialog()==System.Windows.Forms.DialogResult.OK)
			{
				txtRefType.Text = f.ID;
                UpdateRefName();
            }
           



        }

        private void checkBox1_CheckedChanged(object sender, EventArgs e)
        {
            if (!inLoad)
            {
                try
                {
                    mField.Lookup = chkLookup.Checked;
                    mField.LookupSpecified = chkLookup.Checked;
                }
                catch { }
                UpdateNode();
            }
        }

        private void txtLookup_TextChanged(object sender, EventArgs e)
        {
            if (!inLoad)
            {
                try
                {
                    mField.LookupExpression = txtLookup.Text;
                    
                }
                catch
                {
                    
                }
                UpdateNode();
            }
        }

        private void chkIsBrief_CheckedChanged(object sender, EventArgs e)
        {
            if (!inLoad)
            {
                try
                {
                    mField.IsBrief = chkIsBrief.Checked;
                    mField.IsBriefSpecified = chkIsBrief.Checked;
                }
                catch { }
                UpdateNode();
            }
        }

        private void chkUseForPK_CheckedChanged(object sender, EventArgs e)
        {
            if (!inLoad)
            {
                try
                {
                    mField.UseforPK = chkUseForPK.Checked;
                    mField.UseforPKSpecified = chkUseForPK.Checked;
                }
                catch { }
                UpdateNode();
            }

        }

        private void txtEnumName_TextChanged(object sender, EventArgs e)
        {
            if (!inLoad)
            {
                mField.EnumName = txtEnumName.Text;
                UpdateNode();
            }
        }

        private void label5_Click(object sender, EventArgs e)
        {

        }

        private void txtDocumentation_TextChanged(object sender, EventArgs e)
        {
            if (!inLoad)
            {
                mField.Documentation = txtDocumentation.Text;
                UpdateNode();
            }
        }
    }
}
