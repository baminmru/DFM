using System;
using System.Diagnostics;
using System.Drawing;
using System.Windows.Forms;

namespace dv21
{
    //[Microsoft.VisualBasic.CompilerServices.DesignerGenerated()]
    public partial class frmWebAPI_PrimeNG : Form
    {

        // Форма переопределяет dispose для очистки списка компонентов.
        [DebuggerNonUserCode()]
        protected override void Dispose(bool disposing)
        {
            try
            {
                if (disposing )
                {
                    components.Dispose();
                }
            }
            finally
            {
                base.Dispose(disposing);
            }
        }

        // Является обязательной для конструктора форм Windows Forms
        private System.ComponentModel.IContainer components;

        // Примечание: следующая процедура является обязательной для конструктора форм Windows Forms
        // Для ее изменения используйте конструктор форм Windows Form.  
        // Не изменяйте ее в редакторе исходного кода.
        [DebuggerStepThrough()]
        private void InitializeComponent()
        {
            this.cmdGen = new System.Windows.Forms.Button();
            this.button3 = new System.Windows.Forms.Button();
            this.textBoxOutPutFolder = new System.Windows.Forms.TextBox();
            this.Label3 = new System.Windows.Forms.Label();
            this.folderBrowserDialogProjectOutput = new System.Windows.Forms.FolderBrowserDialog();
            this.SuspendLayout();
            // 
            // cmdGen
            // 
            this.cmdGen.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.cmdGen.Location = new System.Drawing.Point(464, 85);
            this.cmdGen.Name = "cmdGen";
            this.cmdGen.Size = new System.Drawing.Size(66, 26);
            this.cmdGen.TabIndex = 77;
            this.cmdGen.Text = "Generate";
            this.cmdGen.UseVisualStyleBackColor = true;
            this.cmdGen.Click += new System.EventHandler(this.cmdGen_Click);
            // 
            // button3
            // 
            this.button3.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.button3.Location = new System.Drawing.Point(455, 21);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(66, 22);
            this.button3.TabIndex = 71;
            this.button3.Text = "...";
            this.button3.Click += new System.EventHandler(this.button3_Click);
            // 
            // textBoxOutPutFolder
            // 
            this.textBoxOutPutFolder.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.textBoxOutPutFolder.Location = new System.Drawing.Point(155, 21);
            this.textBoxOutPutFolder.Name = "textBoxOutPutFolder";
            this.textBoxOutPutFolder.Size = new System.Drawing.Size(288, 20);
            this.textBoxOutPutFolder.TabIndex = 70;
            this.textBoxOutPutFolder.Text = "d:\\BP3\\OUT\\Console\\";
            // 
            // Label3
            // 
            this.Label3.AutoSize = true;
            this.Label3.Location = new System.Drawing.Point(22, 24);
            this.Label3.Name = "Label3";
            this.Label3.Size = new System.Drawing.Size(115, 13);
            this.Label3.TabIndex = 74;
            this.Label3.Text = "Projects Output Folder:";
            // 
            // folderBrowserDialogProjectOutput
            // 
            this.folderBrowserDialogProjectOutput.SelectedPath = "C:\\LATIR2\\Generated\\";
            // 
            // frmWebAPI_PrimeNG
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(542, 123);
            this.Controls.Add(this.cmdGen);
            this.Controls.Add(this.button3);
            this.Controls.Add(this.textBoxOutPutFolder);
            this.Controls.Add(this.Label3);
            this.Name = "frmWebAPI_PrimeNG";
            this.Text = "Web API + ANGULAR 6 + (AMEXIO 5)  generator";
            this.Load += new System.EventHandler(this.frmA4_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        internal Button cmdGen;
        internal Button button3;
        internal TextBox textBoxOutPutFolder;
        internal Label Label3;
        internal FolderBrowserDialog folderBrowserDialogProjectOutput;
    }
}