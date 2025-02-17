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
	/// Summary description for ctlEnum.
	/// </summary>
	public class ctlEnum : System.Windows.Forms.UserControl
	{
		private System.Windows.Forms.Label label1;
		private System.Windows.Forms.Label label6;
		private System.Windows.Forms.TextBox txt1Alias;
		private System.Windows.Forms.Label label3;
		private bool inLoad ;
		private FieldTypeEnum mEnum;
		private System.Windows.Forms.TextBox txtValue;
		public MyTreeNode LastNode;

		private void UpdateNode()
		{
			LastNode.Text=mEnum.Name + "(" + mEnum.Value + ")";  
		}

		/// <summary> 
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		public ctlEnum()
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
			this.txtValue = new System.Windows.Forms.TextBox();
			this.label6 = new System.Windows.Forms.Label();
			this.txt1Alias = new System.Windows.Forms.TextBox();
			this.label3 = new System.Windows.Forms.Label();
			this.SuspendLayout();
			// 
			// label1
			// 
			this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((System.Byte)(204)));
			this.label1.Location = new System.Drawing.Point(0, 8);
			this.label1.Name = "label1";
			this.label1.Size = new System.Drawing.Size(144, 24);
			this.label1.TabIndex = 0;
			this.label1.Text = "Перечисление";
			// 
			// txtValue
			// 
			this.txtValue.Location = new System.Drawing.Point(8, 96);
			this.txtValue.Name = "txtValue";
			this.txtValue.Size = new System.Drawing.Size(272, 20);
			this.txtValue.TabIndex = 3;
			this.txtValue.Text = "";
			this.txtValue.TextChanged += new System.EventHandler(this.txtValue_TextChanged);
			// 
			// label6
			// 
			this.label6.Location = new System.Drawing.Point(8, 72);
			this.label6.Name = "label6";
			this.label6.Size = new System.Drawing.Size(112, 16);
			this.label6.TabIndex = 2;
			this.label6.Text = "Значение";
			// 
			// txt1Alias
			// 
			this.txt1Alias.Location = new System.Drawing.Point(8, 48);
			this.txt1Alias.Name = "txt1Alias";
			this.txt1Alias.Size = new System.Drawing.Size(272, 20);
			this.txt1Alias.TabIndex = 1;
			this.txt1Alias.Text = "";
			this.txt1Alias.TextChanged += new System.EventHandler(this.txt1Alias_TextChanged);
			// 
			// label3
			// 
			this.label3.Location = new System.Drawing.Point(8, 32);
			this.label3.Name = "label3";
			this.label3.Size = new System.Drawing.Size(96, 16);
			this.label3.TabIndex = 0;
			this.label3.Text = "Имя";
			// 
			// ctlEnum
			// 
			this.Controls.AddRange(new System.Windows.Forms.Control[] {
																		  this.txtValue,
																		  this.label6,
																		  this.txt1Alias,
																		  this.label3,
																		  this.label1});
			this.Name = "ctlEnum";
			this.Size = new System.Drawing.Size(288, 160);
			this.Load += new System.EventHandler(this.ctlEnum_Load);
			this.ResumeLayout(false);

		}
		#endregion

		public FieldTypeEnum Enum
		{
			get
			{
					return mEnum;
			}
			set
			{
				mEnum = value;
				if(mEnum != null)
				{
					inLoad= true;
					txt1Alias.Text = mEnum.Name;
					txtValue.Text = mEnum.Value.ToString(); 
					inLoad = false;
				}
			}
		}

		private void txt1Alias_TextChanged(object sender, System.EventArgs e)
		{
			if(!inLoad)
			{
				mEnum.Name  =txt1Alias.Text;
				UpdateNode();
			}
		}

		private void ctlEnum_Load(object sender, System.EventArgs e)
		{
			inLoad = false;
		}

		private void txtValue_TextChanged(object sender, System.EventArgs e)
		{
			if(!inLoad)
			{
				try
				{
					mEnum.Value  =System.Convert.ToInt16(txtValue.Text,10);
				}
				catch
				{
					txtValue.Text="0";
					mEnum.Value  =0;
				}
				UpdateNode();
			}
		}
	}
}
