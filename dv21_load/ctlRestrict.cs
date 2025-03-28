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
	/// Summary description for ctlRestrict.
	/// </summary>
	public class ctlRestrict : System.Windows.Forms.UserControl
	{
		private System.Windows.Forms.Label label1;
		private System.Windows.Forms.Label label2;
		private System.Windows.Forms.ComboBox cmbType;
		private System.Windows.Forms.CheckBox chkRun;
		private System.Windows.Forms.Label label3;
		private System.Windows.Forms.TextBox txtID;
		private System.Windows.Forms.CheckBox chkCreate;
		private System.Windows.Forms.CheckBox chkDelete;
		private System.Windows.Forms.CheckBox chkUpdate;
		private System.Windows.Forms.CheckBox chkRead;
		private System.Windows.Forms.CheckBox chkWrite;
		private bool inLoad;
		private dv21.ModeTypeRestrict mRestrict; 
		public MyTreeNode LastNode;


		private void UpdateNode()
		{
			LastNode.Text=  mRestrict.Type.ToString();
            frmCard f = (frmCard)this.ParentForm;
            f.Saved = false;
		}

		/// <summary> 
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		public ctlRestrict()
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
			this.label2 = new System.Windows.Forms.Label();
			this.cmbType = new System.Windows.Forms.ComboBox();
			this.chkRun = new System.Windows.Forms.CheckBox();
			this.label3 = new System.Windows.Forms.Label();
			this.txtID = new System.Windows.Forms.TextBox();
			this.chkCreate = new System.Windows.Forms.CheckBox();
			this.chkDelete = new System.Windows.Forms.CheckBox();
			this.chkUpdate = new System.Windows.Forms.CheckBox();
			this.chkRead = new System.Windows.Forms.CheckBox();
			this.chkWrite = new System.Windows.Forms.CheckBox();
			this.SuspendLayout();
			// 
			// label1
			// 
			this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((System.Byte)(204)));
			this.label1.Location = new System.Drawing.Point(0, 8);
			this.label1.Name = "label1";
			this.label1.Size = new System.Drawing.Size(144, 24);
			this.label1.TabIndex = 0;
			this.label1.Text = "Ограничение";
			// 
			// label2
			// 
			this.label2.Location = new System.Drawing.Point(16, 32);
			this.label2.Name = "label2";
			this.label2.Size = new System.Drawing.Size(144, 16);
			this.label2.TabIndex = 0;
			this.label2.Text = "Тип ограничения";
			// 
			// cmbType
			// 
			this.cmbType.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
			this.cmbType.Items.AddRange(new object[] {
														 "action",
														 "section",
														 "field"});
			this.cmbType.Location = new System.Drawing.Point(16, 48);
			this.cmbType.Name = "cmbType";
			this.cmbType.Size = new System.Drawing.Size(240, 21);
			this.cmbType.TabIndex = 1;
			this.cmbType.SelectedIndexChanged += new System.EventHandler(this.cmbType_SelectedIndexChanged);
			// 
			// chkRun
			// 
			this.chkRun.Location = new System.Drawing.Point(16, 144);
			this.chkRun.Name = "chkRun";
			this.chkRun.Size = new System.Drawing.Size(176, 16);
			this.chkRun.TabIndex = 4;
			this.chkRun.Text = "Run";
			this.chkRun.CheckedChanged += new System.EventHandler(this.chkRun_CheckedChanged);
			// 
			// label3
			// 
			this.label3.Location = new System.Drawing.Point(16, 80);
			this.label3.Name = "label3";
			this.label3.Size = new System.Drawing.Size(216, 16);
			this.label3.TabIndex = 2;
			this.label3.Text = "Идентификатор ресурса";
			// 
			// txtID
			// 
			this.txtID.Location = new System.Drawing.Point(16, 104);
			this.txtID.Name = "txtID";
			this.txtID.Size = new System.Drawing.Size(240, 20);
			this.txtID.TabIndex = 3;
			this.txtID.Text = "";
			this.txtID.TextChanged += new System.EventHandler(this.txtID_TextChanged);
			// 
			// chkCreate
			// 
			this.chkCreate.Location = new System.Drawing.Point(16, 168);
			this.chkCreate.Name = "chkCreate";
			this.chkCreate.Size = new System.Drawing.Size(176, 16);
			this.chkCreate.TabIndex = 5;
			this.chkCreate.Text = "Create";
			this.chkCreate.CheckedChanged += new System.EventHandler(this.chkCreate_CheckedChanged);
			// 
			// chkDelete
			// 
			this.chkDelete.Location = new System.Drawing.Point(16, 216);
			this.chkDelete.Name = "chkDelete";
			this.chkDelete.Size = new System.Drawing.Size(176, 16);
			this.chkDelete.TabIndex = 7;
			this.chkDelete.Text = "Delete";
			this.chkDelete.CheckedChanged += new System.EventHandler(this.chkDelete_CheckedChanged);
			// 
			// chkUpdate
			// 
			this.chkUpdate.Location = new System.Drawing.Point(16, 192);
			this.chkUpdate.Name = "chkUpdate";
			this.chkUpdate.Size = new System.Drawing.Size(176, 16);
			this.chkUpdate.TabIndex = 6;
			this.chkUpdate.Text = "Update";
			this.chkUpdate.CheckedChanged += new System.EventHandler(this.chkUpdate_CheckedChanged);
			// 
			// chkRead
			// 
			this.chkRead.Location = new System.Drawing.Point(16, 240);
			this.chkRead.Name = "chkRead";
			this.chkRead.Size = new System.Drawing.Size(176, 16);
			this.chkRead.TabIndex = 8;
			this.chkRead.Text = "Read";
			this.chkRead.CheckedChanged += new System.EventHandler(this.chkRead_CheckedChanged);
			// 
			// chkWrite
			// 
			this.chkWrite.Location = new System.Drawing.Point(16, 264);
			this.chkWrite.Name = "chkWrite";
			this.chkWrite.Size = new System.Drawing.Size(176, 24);
			this.chkWrite.TabIndex = 9;
			this.chkWrite.Text = "Write";
			this.chkWrite.CheckedChanged += new System.EventHandler(this.chkWrite_CheckedChanged);
			// 
			// ctlRestrict
			// 
			this.Controls.AddRange(new System.Windows.Forms.Control[] {
																		  this.chkWrite,
																		  this.chkRead,
																		  this.chkUpdate,
																		  this.chkDelete,
																		  this.chkCreate,
																		  this.txtID,
																		  this.label3,
																		  this.chkRun,
																		  this.cmbType,
																		  this.label2,
																		  this.label1});
			this.Name = "ctlRestrict";
			this.Size = new System.Drawing.Size(280, 296);
			this.Load += new System.EventHandler(this.ctlRestrict_Load);
			this.ResumeLayout(false);

		}
		#endregion

		private void ctlRestrict_Load(object sender, System.EventArgs e)
		{
			inLoad = false;
		}

		private void SaveChanges()
		{
			mRestrict.Type=(dv21.ModeTypeRestrictType)  cmbType.SelectedIndex;
			mRestrict.AllowCreate=chkCreate.Checked;
			mRestrict.AllowDelete=chkDelete.Checked  ; 
			mRestrict.AllowRead=chkRead.Checked ;
			mRestrict.AllowRun=chkRun.Checked ;
			mRestrict.AllowUpdate=chkUpdate.Checked;
			mRestrict.AllowWrite=chkWrite.Checked ;
			mRestrict.AllowCreateSpecified =true;
			mRestrict.AllowDeleteSpecified =true; 
			mRestrict.AllowReadSpecified =true;
			mRestrict.AllowRunSpecified =true;
			mRestrict.AllowUpdateSpecified =true;
			mRestrict.AllowWriteSpecified =true ;
			mRestrict.ID=txtID.Text ;
			UpdateNode();
		}

		private void cmbType_SelectedIndexChanged(object sender, System.EventArgs e)
		{
			if(!inLoad) SaveChanges(); 
		}

		private void txtID_TextChanged(object sender, System.EventArgs e)
		{
			if(!inLoad) SaveChanges();
		}

		private void chkRun_CheckedChanged(object sender, System.EventArgs e)
		{
			if(!inLoad) SaveChanges();
		}

		private void chkCreate_CheckedChanged(object sender, System.EventArgs e)
		{
			if(!inLoad) SaveChanges();
		}

		private void chkUpdate_CheckedChanged(object sender, System.EventArgs e)
		{
			if(!inLoad) SaveChanges();
		}

		private void chkDelete_CheckedChanged(object sender, System.EventArgs e)
		{
			if(!inLoad) SaveChanges();
		}

		private void chkRead_CheckedChanged(object sender, System.EventArgs e)
		{
			if(!inLoad) SaveChanges();
		}

		private void chkWrite_CheckedChanged(object sender, System.EventArgs e)
		{
			if(!inLoad) SaveChanges();
		}

		public dv21.ModeTypeRestrict  Restrict
		{
			get 
			{	
				return mRestrict;
			}
			set
			{
				mRestrict = value;
				if(mRestrict != null)
				{
					inLoad = true;
					txtID.Text=mRestrict.ID ;
					cmbType.Text = mRestrict.Type.ToString();
					chkCreate.Checked=mRestrict.AllowCreate;
					chkDelete.Checked =mRestrict.AllowDelete ; 
					chkRead.Checked =mRestrict.AllowRead ;
					chkRun.Checked =mRestrict.AllowRun  ;
					chkUpdate.Checked =mRestrict.AllowUpdate;
					chkWrite.Checked =mRestrict.AllowWrite ; 
					inLoad = false;
				}
			}
		}
	}
}
