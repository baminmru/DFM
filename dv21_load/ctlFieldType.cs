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
		private System.Windows.Forms.Label label10;
		private System.Windows.Forms.TextBox txtRefSection;
		private System.Windows.Forms.Button cmdStructID;
		private System.Windows.Forms.Button cmdRefType;
        public MyTreeNode LastNode;

		private void UpdateNode()
		{
			LastNode.Text=mField.Name[0].Value + "(" + mField.Name[0].Language + ")" ;
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
					cmbType.Text = mField.Type.ToString();
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
			this.label10 = new System.Windows.Forms.Label();
			this.txtRefSection = new System.Windows.Forms.TextBox();
			this.cmdStructID = new System.Windows.Forms.Button();
			this.cmdRefType = new System.Windows.Forms.Button();
			this.SuspendLayout();
			// 
			// label1
			// 
			this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((System.Byte)(204)));
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
														 "refid",
														 "image",
														 "text"});
			this.cmbType.Location = new System.Drawing.Point(8, 128);
			this.cmbType.Name = "cmbType";
			this.cmbType.Size = new System.Drawing.Size(272, 21);
			this.cmbType.TabIndex = 6;
			this.cmbType.SelectedIndexChanged += new System.EventHandler(this.cmbType_SelectedIndexChanged);
			// 
			// label2
			// 
			this.label2.Location = new System.Drawing.Point(8, 104);
			this.label2.Name = "label2";
			this.label2.Size = new System.Drawing.Size(88, 16);
			this.label2.TabIndex = 5;
			this.label2.Text = "Тип";
			// 
			// cmb1Names
			// 
			this.cmb1Names.Enabled = false;
			this.cmb1Names.Location = new System.Drawing.Point(8, 352);
			this.cmb1Names.Name = "cmb1Names";
			this.cmb1Names.Size = new System.Drawing.Size(224, 95);
			this.cmb1Names.TabIndex = 12;
			// 
			// label5
			// 
			this.label5.Location = new System.Drawing.Point(8, 336);
			this.label5.Name = "label5";
			this.label5.Size = new System.Drawing.Size(80, 16);
			this.label5.TabIndex = 11;
			this.label5.Text = "Названия";
			// 
			// cmd1Names
			// 
			this.cmd1Names.Location = new System.Drawing.Point(240, 352);
			this.cmd1Names.Name = "cmd1Names";
			this.cmd1Names.Size = new System.Drawing.Size(40, 24);
			this.cmd1Names.TabIndex = 13;
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
			this.txt1ID.Size = new System.Drawing.Size(224, 20);
			this.txt1ID.TabIndex = 3;
			this.txt1ID.Text = "";
			this.txt1ID.TextChanged += new System.EventHandler(this.txt1ID_TextChanged);
			// 
			// txt1Alias
			// 
			this.txt1Alias.Location = new System.Drawing.Point(8, 56);
			this.txt1Alias.Name = "txt1Alias";
			this.txt1Alias.Size = new System.Drawing.Size(272, 20);
			this.txt1Alias.TabIndex = 1;
			this.txt1Alias.Text = "";
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
			this.txtMax.Size = new System.Drawing.Size(272, 20);
			this.txtMax.TabIndex = 8;
			this.txtMax.Text = "";
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
			this.chkNotNull.Location = new System.Drawing.Point(96, 192);
			this.chkNotNull.Name = "chkNotNull";
			this.chkNotNull.Size = new System.Drawing.Size(24, 16);
			this.chkNotNull.TabIndex = 10;
			this.chkNotNull.CheckedChanged += new System.EventHandler(this.chkNotNull_CheckedChanged);
			// 
			// chkReference
			// 
			this.chkReference.Location = new System.Drawing.Point(96, 216);
			this.chkReference.Name = "chkReference";
			this.chkReference.Size = new System.Drawing.Size(24, 16);
			this.chkReference.TabIndex = 15;
			this.chkReference.CheckedChanged += new System.EventHandler(this.chkReference_CheckedChanged);
			// 
			// label8
			// 
			this.label8.Location = new System.Drawing.Point(8, 216);
			this.label8.Name = "label8";
			this.label8.Size = new System.Drawing.Size(96, 16);
			this.label8.TabIndex = 14;
			this.label8.Text = "Ссылка";
			// 
			// label9
			// 
			this.label9.Location = new System.Drawing.Point(8, 240);
			this.label9.Name = "label9";
			this.label9.Size = new System.Drawing.Size(280, 24);
			this.label9.TabIndex = 16;
			this.label9.Text = "Тип, куда ссылаемся (ID)";
			// 
			// txtRefType
			// 
			this.txtRefType.Location = new System.Drawing.Point(8, 256);
			this.txtRefType.Name = "txtRefType";
			this.txtRefType.Size = new System.Drawing.Size(232, 20);
			this.txtRefType.TabIndex = 17;
			this.txtRefType.Text = "";
			this.txtRefType.TextChanged += new System.EventHandler(this.txtRefType_TextChanged);
			// 
			// label10
			// 
			this.label10.Location = new System.Drawing.Point(8, 280);
			this.label10.Name = "label10";
			this.label10.Size = new System.Drawing.Size(288, 16);
			this.label10.TabIndex = 18;
			this.label10.Text = "Раздел, куда ссылаемся (ID)";
			// 
			// txtRefSection
			// 
			this.txtRefSection.Location = new System.Drawing.Point(8, 304);
			this.txtRefSection.Name = "txtRefSection";
			this.txtRefSection.Size = new System.Drawing.Size(232, 20);
			this.txtRefSection.TabIndex = 19;
			this.txtRefSection.Text = "";
			this.txtRefSection.TextChanged += new System.EventHandler(this.txtRefSection_TextChanged);
			// 
			// cmdStructID
			// 
			this.cmdStructID.Location = new System.Drawing.Point(248, 304);
			this.cmdStructID.Name = "cmdStructID";
			this.cmdStructID.Size = new System.Drawing.Size(32, 24);
			this.cmdStructID.TabIndex = 20;
			this.cmdStructID.Text = "...";
			this.cmdStructID.Click += new System.EventHandler(this.cmdStructID_Click);
			// 
			// cmdRefType
			// 
			this.cmdRefType.Location = new System.Drawing.Point(248, 256);
			this.cmdRefType.Name = "cmdRefType";
			this.cmdRefType.Size = new System.Drawing.Size(32, 24);
			this.cmdRefType.TabIndex = 21;
			this.cmdRefType.Text = "...";
			this.cmdRefType.Click += new System.EventHandler(this.cmdRefType_Click);
			// 
			// ctlFieldType
			// 
			this.Controls.AddRange(new System.Windows.Forms.Control[] {
																		  this.cmdRefType,
																		  this.cmdStructID,
																		  this.txtRefSection,
																		  this.label10,
																		  this.txtRefType,
																		  this.label9,
																		  this.chkReference,
																		  this.label8,
																		  this.chkNotNull,
																		  this.label7,
																		  this.txtMax,
																		  this.label6,
																		  this.cmbType,
																		  this.label2,
																		  this.cmb1Names,
																		  this.label5,
																		  this.cmd1Names,
																		  this.cmd1NewID,
																		  this.txt1ID,
																		  this.txt1Alias,
																		  this.label4,
																		  this.label3,
																		  this.label1});
			this.Name = "ctlFieldType";
			this.Size = new System.Drawing.Size(296, 456);
			this.Load += new System.EventHandler(this.ctlFieldType_Load);
			this.ResumeLayout(false);

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
					mField.Reference    =chkNotNull.Checked;
					mField.ReferenceSpecified =true;
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
			}

		}

		private void cmdRefType_Click(object sender, System.EventArgs e)
		{
			dv21_load.frmSectionView f = new dv21_load.frmSectionView();
			f.TypeOnly= true;
			if(f.ShowDialog()==System.Windows.Forms.DialogResult.OK)
			{
				txtRefType.Text = f.ID;
			}
		}

	}
}
