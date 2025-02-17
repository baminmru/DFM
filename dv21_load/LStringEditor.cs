using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using dv21;
using dv21_util; 


//#define ADDROW(Type,Arr) ArrayList lsa = new ArrayList(); lsa.InsertRange(0,Arr); lsa.Add(new Type() ); Arr = new Type[lsa.Count]; lsa.CopyTo(Arr,0);

namespace dv21_ls
{
	/// <summary>
	/// Summary description for LStringEditor.
	/// </summary>
	public class LStringEditor : System.Windows.Forms.Form
	{
		private System.Windows.Forms.ComboBox cmbLang;
		private System.Windows.Forms.Label label1;
		private System.Windows.Forms.ListBox lstStrings;
		private System.Windows.Forms.Label label2;
		private System.Windows.Forms.Button cmdAdd;
		private System.Windows.Forms.Button cmdDel;
		private System.Windows.Forms.TextBox txtValue;
		private System.Windows.Forms.Label label3;
		private dv21.LocalizedStringsLocalizedString[] mLstring;
		private bool inClick;
		private System.Windows.Forms.Button cmdOK;

		public dv21.LocalizedStringsLocalizedString[]   LString
		{
			get 
			{
				return mLstring;
			}
			set 
			{
				mLstring=value;
			}
		}

		/// <summary>
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		public LStringEditor()
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
			System.Resources.ResourceManager resources = new System.Resources.ResourceManager(typeof(LStringEditor));
			this.cmbLang = new System.Windows.Forms.ComboBox();
			this.label1 = new System.Windows.Forms.Label();
			this.lstStrings = new System.Windows.Forms.ListBox();
			this.label2 = new System.Windows.Forms.Label();
			this.cmdAdd = new System.Windows.Forms.Button();
			this.cmdDel = new System.Windows.Forms.Button();
			this.txtValue = new System.Windows.Forms.TextBox();
			this.label3 = new System.Windows.Forms.Label();
			this.cmdOK = new System.Windows.Forms.Button();
			this.SuspendLayout();
			// 
			// cmbLang
			// 
			this.cmbLang.Items.AddRange(new object[] {
														 "by",
														 "ca",
														 "en",
														 "fi",
														 "hu",
														 "ru",
														 "us"});
			this.cmbLang.Location = new System.Drawing.Point(16, 176);
			this.cmbLang.Name = "cmbLang";
			this.cmbLang.Size = new System.Drawing.Size(184, 21);
			this.cmbLang.Sorted = true;
			this.cmbLang.TabIndex = 1;
			this.cmbLang.TextChanged += new System.EventHandler(this.cmbLang_TextChanged);
			this.cmbLang.SelectedIndexChanged += new System.EventHandler(this.cmbLang_SelectedIndexChanged);
			// 
			// label1
			// 
			this.label1.Image = ((System.Drawing.Bitmap)(resources.GetObject("label1.Image")));
			this.label1.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
			this.label1.Location = new System.Drawing.Point(16, 136);
			this.label1.Name = "label1";
			this.label1.Size = new System.Drawing.Size(120, 32);
			this.label1.TabIndex = 0;
			this.label1.Text = "&Language";
			this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
			// 
			// lstStrings
			// 
			this.lstStrings.Location = new System.Drawing.Point(16, 48);
			this.lstStrings.Name = "lstStrings";
			this.lstStrings.Size = new System.Drawing.Size(384, 82);
			this.lstStrings.TabIndex = 8;
			this.lstStrings.SelectedIndexChanged += new System.EventHandler(this.lstStrings_SelectedIndexChanged);
			// 
			// label2
			// 
			this.label2.Image = ((System.Drawing.Bitmap)(resources.GetObject("label2.Image")));
			this.label2.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
			this.label2.Location = new System.Drawing.Point(16, 8);
			this.label2.Name = "label2";
			this.label2.Size = new System.Drawing.Size(200, 32);
			this.label2.TabIndex = 5;
			this.label2.Text = "Localized &Strings";
			this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
			// 
			// cmdAdd
			// 
			this.cmdAdd.Image = ((System.Drawing.Bitmap)(resources.GetObject("cmdAdd.Image")));
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
			this.cmdDel.Image = ((System.Drawing.Bitmap)(resources.GetObject("cmdDel.Image")));
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
			this.txtValue.Location = new System.Drawing.Point(208, 176);
			this.txtValue.Name = "txtValue";
			this.txtValue.Size = new System.Drawing.Size(192, 20);
			this.txtValue.TabIndex = 3;
			this.txtValue.Text = "";
			this.txtValue.TextChanged += new System.EventHandler(this.txtValue_TextChanged);
			// 
			// label3
			// 
			this.label3.Image = ((System.Drawing.Bitmap)(resources.GetObject("label3.Image")));
			this.label3.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
			this.label3.Location = new System.Drawing.Point(208, 136);
			this.label3.Name = "label3";
			this.label3.Size = new System.Drawing.Size(112, 32);
			this.label3.TabIndex = 2;
			this.label3.Text = "&Value";
			this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
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
			// LStringEditor
			// 
			this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
			this.ClientSize = new System.Drawing.Size(410, 239);
			this.Controls.AddRange(new System.Windows.Forms.Control[] {
																		  this.cmdOK,
																		  this.label3,
																		  this.txtValue,
																		  this.cmdDel,
																		  this.cmdAdd,
																		  this.label2,
																		  this.lstStrings,
																		  this.label1,
																		  this.cmbLang});
			this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
			this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
			this.MaximizeBox = false;
			this.MinimizeBox = false;
			this.Name = "LStringEditor";
			this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
			this.Text = "Localized String Editor";
			this.Load += new System.EventHandler(this.LStringEditor_Load);
			this.ResumeLayout(false);

		}
		#endregion

		private void LStringEditor_Load(object sender, System.EventArgs e)
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
			if (LString != null)
			{
				inClick = true;
				lstStrings.Items.Clear();
				int i;
				LocalizedStringsLocalizedString ls;
				
				for(i=0;i<LString.Length ;i++)
				{
					ls=(LocalizedStringsLocalizedString)LString[i];
					lstStrings.Items.Add("("+ls.Language + ")" +ls.Value);
				}
				inClick = false;
			}
		}

		private void cmdAdd_Click(object sender, System.EventArgs e)
		{
			if(LString !=null)
			{
			LString = (LocalizedStringsLocalizedString[])MyUtils.Add(LString,	new LocalizedStringsLocalizedString(),new LocalizedStringsLocalizedString[LString.Length+1]);
            lstStrings.SelectedIndex = lstStrings.Items.Add("()");
			}
		}

		private void cmdDel_Click(object sender, System.EventArgs e)
		{
			if(LString !=null)
			{
				if(lstStrings.SelectedIndex >=0) 
				{

				
					LString = (LocalizedStringsLocalizedString[])MyUtils.RemoveAt(LString,	lstStrings.SelectedIndex,new LocalizedStringsLocalizedString[LString.Length-1]);
					InitList();
				}
			}
		}

		private void cmbLang_SelectedIndexChanged(object sender, System.EventArgs e)
		{
		
		}

		private void cmbLang_TextChanged(object sender, System.EventArgs e)
		{
			if (!inClick)
			{
				if(lstStrings.SelectedIndex >=0) 
				{

					int i=lstStrings.SelectedIndex;
					LocalizedStringsLocalizedString ls;
					ls = (LocalizedStringsLocalizedString)LString[i];
					ls.Language=cmbLang.Text;
					lstStrings.Items[i]="("+ls.Language + ")" +ls.Value;
					
				}
			}
		}

		private void txtValue_TextChanged(object sender, System.EventArgs e)
		{
			if (!inClick)
			{
				if(lstStrings.SelectedIndex >=0) 
				{
					int i=lstStrings.SelectedIndex;
					LocalizedStringsLocalizedString ls;
					ls = (LocalizedStringsLocalizedString)LString[i];
					ls.Value=txtValue.Text;
	  		        lstStrings.Items[i]="("+ls.Language + ")" +ls.Value;

				}
			}
		}

		private void lstStrings_SelectedIndexChanged(object sender, System.EventArgs e)
		{
			inClick = true;
			try
			{
				int i=lstStrings.SelectedIndex;
				LocalizedStringsLocalizedString ls;
				ls = (LocalizedStringsLocalizedString)LString[i];
				txtValue.Text=ls.Value;
				cmbLang.Text=ls.Language;
			}
			catch{}
			inClick = false;
		}

		private void cmdOK_Click(object sender, System.EventArgs e)
		{
			this.Hide(); 
		}

		
	}
}
