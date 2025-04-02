using System;
using System.Diagnostics;
using System.Drawing;
using System.Windows.Forms;

namespace dv21
{
    
    internal partial class frmPGImport
    {
        #region Windows Form Designer generated code 
        
        public frmPGImport() : base()
        {
            // This call is required by the Windows Form Designer.
            InitializeComponent();
        }
        
        // Required by the Windows Form Designer
        private System.ComponentModel.IContainer components;
        public ToolTip ToolTip1;
        public CheckedListBox lstBlocks;
        public ProgressBar pb;
        public Label Label3;
        public GroupBox Frame1;
        public TextBox txtLog;
        public Button cmdGo;
        public TextBox txtLogin;
        public TextBox txtPassword;
        public TextBox txtDatabase;
        public Label lblServer;
        public Label lblLogin;
        public Label lblPassword;
        public Label lblDatabase;
        public GroupBox frameRight;
        public OpenFileDialog DlgOpen;
        public Label Label2;
        // NOTE: The following procedure is required by the Windows Form Designer
        // It can be modified using the Windows Form Designer.
        // Do not modify it using the code editor.
        [DebuggerStepThrough()]
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.ToolTip1 = new System.Windows.Forms.ToolTip(this.components);
            this.Frame1 = new System.Windows.Forms.GroupBox();
            this.lstBlocks = new System.Windows.Forms.CheckedListBox();
            this.pb = new System.Windows.Forms.ProgressBar();
            this.Label3 = new System.Windows.Forms.Label();
            this.txtLog = new System.Windows.Forms.TextBox();
            this.cmdGo = new System.Windows.Forms.Button();
            this.frameRight = new System.Windows.Forms.GroupBox();
            this.txtServer = new System.Windows.Forms.TextBox();
            this.Label4 = new System.Windows.Forms.Label();
            this.txtPort = new System.Windows.Forms.TextBox();
            this.txtLogin = new System.Windows.Forms.TextBox();
            this.txtPassword = new System.Windows.Forms.TextBox();
            this.txtDatabase = new System.Windows.Forms.TextBox();
            this.lblServer = new System.Windows.Forms.Label();
            this.lblLogin = new System.Windows.Forms.Label();
            this.lblPassword = new System.Windows.Forms.Label();
            this.lblDatabase = new System.Windows.Forms.Label();
            this.DlgOpen = new System.Windows.Forms.OpenFileDialog();
            this.Label2 = new System.Windows.Forms.Label();
            this.lblmsg = new System.Windows.Forms.Label();
            this.cmdConnect = new System.Windows.Forms.Button();
            this.Frame1.SuspendLayout();
            this.frameRight.SuspendLayout();
            this.SuspendLayout();
            // 
            // Frame1
            // 
            this.Frame1.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.Frame1.BackColor = System.Drawing.SystemColors.Control;
            this.Frame1.Controls.Add(this.lstBlocks);
            this.Frame1.Controls.Add(this.pb);
            this.Frame1.Controls.Add(this.Label3);
            this.Frame1.ForeColor = System.Drawing.SystemColors.ControlText;
            this.Frame1.Location = new System.Drawing.Point(280, 0);
            this.Frame1.Name = "Frame1";
            this.Frame1.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.Frame1.Size = new System.Drawing.Size(273, 305);
            this.Frame1.TabIndex = 16;
            this.Frame1.TabStop = false;
            this.Frame1.Text = "Процесс инсталляции";
            // 
            // lstBlocks
            // 
            this.lstBlocks.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.lstBlocks.BackColor = System.Drawing.SystemColors.Window;
            this.lstBlocks.CheckOnClick = true;
            this.lstBlocks.Cursor = System.Windows.Forms.Cursors.Default;
            this.lstBlocks.ForeColor = System.Drawing.SystemColors.WindowText;
            this.lstBlocks.Location = new System.Drawing.Point(8, 32);
            this.lstBlocks.Name = "lstBlocks";
            this.lstBlocks.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.lstBlocks.ScrollAlwaysVisible = true;
            this.lstBlocks.Size = new System.Drawing.Size(257, 244);
            this.lstBlocks.TabIndex = 7;
            this.lstBlocks.ThreeDCheckBoxes = true;
            this.lstBlocks.SelectedIndexChanged += new System.EventHandler(this.lstBlocks_SelectedIndexChanged);
            // 
            // pb
            // 
            this.pb.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.pb.Location = new System.Drawing.Point(6, 280);
            this.pb.Name = "pb";
            this.pb.Size = new System.Drawing.Size(257, 17);
            this.pb.TabIndex = 8;
            this.pb.Visible = false;
            // 
            // Label3
            // 
            this.Label3.BackColor = System.Drawing.SystemColors.Control;
            this.Label3.Cursor = System.Windows.Forms.Cursors.Default;
            this.Label3.ForeColor = System.Drawing.SystemColors.ControlText;
            this.Label3.Location = new System.Drawing.Point(8, 16);
            this.Label3.Name = "Label3";
            this.Label3.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.Label3.Size = new System.Drawing.Size(185, 17);
            this.Label3.TabIndex = 19;
            this.Label3.Text = "Схемы";
            // 
            // txtLog
            // 
            this.txtLog.AcceptsReturn = true;
            this.txtLog.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.txtLog.BackColor = System.Drawing.SystemColors.Window;
            this.txtLog.Cursor = System.Windows.Forms.Cursors.IBeam;
            this.txtLog.ForeColor = System.Drawing.SystemColors.WindowText;
            this.txtLog.Location = new System.Drawing.Point(12, 333);
            this.txtLog.MaxLength = 0;
            this.txtLog.Multiline = true;
            this.txtLog.Name = "txtLog";
            this.txtLog.ReadOnly = true;
            this.txtLog.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.txtLog.ScrollBars = System.Windows.Forms.ScrollBars.Both;
            this.txtLog.Size = new System.Drawing.Size(533, 65);
            this.txtLog.TabIndex = 9;
            this.txtLog.WordWrap = false;
            // 
            // cmdGo
            // 
            this.cmdGo.BackColor = System.Drawing.SystemColors.Control;
            this.cmdGo.Cursor = System.Windows.Forms.Cursors.Default;
            this.cmdGo.ForeColor = System.Drawing.SystemColors.ControlText;
            this.cmdGo.Location = new System.Drawing.Point(12, 272);
            this.cmdGo.Name = "cmdGo";
            this.cmdGo.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.cmdGo.Size = new System.Drawing.Size(256, 25);
            this.cmdGo.TabIndex = 6;
            this.cmdGo.Text = "Импорт";
            this.cmdGo.UseVisualStyleBackColor = false;
            this.cmdGo.Click += new System.EventHandler(this.cmdGo_Click);
            // 
            // frameRight
            // 
            this.frameRight.BackColor = System.Drawing.SystemColors.Control;
            this.frameRight.Controls.Add(this.txtServer);
            this.frameRight.Controls.Add(this.Label4);
            this.frameRight.Controls.Add(this.txtPort);
            this.frameRight.Controls.Add(this.txtLogin);
            this.frameRight.Controls.Add(this.txtPassword);
            this.frameRight.Controls.Add(this.txtDatabase);
            this.frameRight.Controls.Add(this.lblServer);
            this.frameRight.Controls.Add(this.lblLogin);
            this.frameRight.Controls.Add(this.lblPassword);
            this.frameRight.Controls.Add(this.lblDatabase);
            this.frameRight.ForeColor = System.Drawing.SystemColors.ControlText;
            this.frameRight.Location = new System.Drawing.Point(0, 0);
            this.frameRight.Name = "frameRight";
            this.frameRight.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.frameRight.Size = new System.Drawing.Size(277, 215);
            this.frameRight.TabIndex = 15;
            this.frameRight.TabStop = false;
            this.frameRight.Text = "Параметры подключения";
            // 
            // txtServer
            // 
            this.txtServer.AcceptsReturn = true;
            this.txtServer.BackColor = System.Drawing.SystemColors.Window;
            this.txtServer.Cursor = System.Windows.Forms.Cursors.IBeam;
            this.txtServer.ForeColor = System.Drawing.SystemColors.WindowText;
            this.txtServer.ImeMode = System.Windows.Forms.ImeMode.Disable;
            this.txtServer.Location = new System.Drawing.Point(11, 32);
            this.txtServer.MaxLength = 0;
            this.txtServer.Name = "txtServer";
            this.txtServer.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.txtServer.Size = new System.Drawing.Size(257, 20);
            this.txtServer.TabIndex = 12;
            // 
            // Label4
            // 
            this.Label4.BackColor = System.Drawing.SystemColors.Control;
            this.Label4.Cursor = System.Windows.Forms.Cursors.Default;
            this.Label4.ForeColor = System.Drawing.SystemColors.ControlText;
            this.Label4.Location = new System.Drawing.Point(13, 67);
            this.Label4.Name = "Label4";
            this.Label4.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.Label4.Size = new System.Drawing.Size(96, 17);
            this.Label4.TabIndex = 11;
            this.Label4.Text = "Порт:";
            // 
            // txtPort
            // 
            this.txtPort.AcceptsReturn = true;
            this.txtPort.BackColor = System.Drawing.SystemColors.Window;
            this.txtPort.Cursor = System.Windows.Forms.Cursors.IBeam;
            this.txtPort.ForeColor = System.Drawing.SystemColors.WindowText;
            this.txtPort.Location = new System.Drawing.Point(142, 64);
            this.txtPort.MaxLength = 0;
            this.txtPort.Name = "txtPort";
            this.txtPort.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.txtPort.Size = new System.Drawing.Size(126, 20);
            this.txtPort.TabIndex = 10;
            this.txtPort.Text = "5432";
            // 
            // txtLogin
            // 
            this.txtLogin.AcceptsReturn = true;
            this.txtLogin.BackColor = System.Drawing.SystemColors.Window;
            this.txtLogin.Cursor = System.Windows.Forms.Cursors.IBeam;
            this.txtLogin.ForeColor = System.Drawing.SystemColors.WindowText;
            this.txtLogin.Location = new System.Drawing.Point(12, 140);
            this.txtLogin.MaxLength = 0;
            this.txtLogin.Name = "txtLogin";
            this.txtLogin.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.txtLogin.Size = new System.Drawing.Size(257, 20);
            this.txtLogin.TabIndex = 2;
            this.txtLogin.Text = "postgres";
            // 
            // txtPassword
            // 
            this.txtPassword.AcceptsReturn = true;
            this.txtPassword.BackColor = System.Drawing.SystemColors.Window;
            this.txtPassword.Cursor = System.Windows.Forms.Cursors.IBeam;
            this.txtPassword.ForeColor = System.Drawing.SystemColors.WindowText;
            this.txtPassword.ImeMode = System.Windows.Forms.ImeMode.Disable;
            this.txtPassword.Location = new System.Drawing.Point(12, 183);
            this.txtPassword.MaxLength = 0;
            this.txtPassword.Name = "txtPassword";
            this.txtPassword.PasswordChar = '*';
            this.txtPassword.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.txtPassword.Size = new System.Drawing.Size(257, 20);
            this.txtPassword.TabIndex = 3;
            // 
            // txtDatabase
            // 
            this.txtDatabase.AcceptsReturn = true;
            this.txtDatabase.BackColor = System.Drawing.SystemColors.Window;
            this.txtDatabase.Cursor = System.Windows.Forms.Cursors.IBeam;
            this.txtDatabase.ForeColor = System.Drawing.SystemColors.WindowText;
            this.txtDatabase.Location = new System.Drawing.Point(12, 103);
            this.txtDatabase.MaxLength = 0;
            this.txtDatabase.Name = "txtDatabase";
            this.txtDatabase.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.txtDatabase.Size = new System.Drawing.Size(257, 20);
            this.txtDatabase.TabIndex = 1;
            // 
            // lblServer
            // 
            this.lblServer.BackColor = System.Drawing.SystemColors.Control;
            this.lblServer.Cursor = System.Windows.Forms.Cursors.Default;
            this.lblServer.ForeColor = System.Drawing.SystemColors.ControlText;
            this.lblServer.Location = new System.Drawing.Point(12, 16);
            this.lblServer.Name = "lblServer";
            this.lblServer.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.lblServer.Size = new System.Drawing.Size(257, 17);
            this.lblServer.TabIndex = 0;
            this.lblServer.Text = "Postgre SQL сервер:";
            // 
            // lblLogin
            // 
            this.lblLogin.BackColor = System.Drawing.SystemColors.Control;
            this.lblLogin.Cursor = System.Windows.Forms.Cursors.Default;
            this.lblLogin.ForeColor = System.Drawing.SystemColors.ControlText;
            this.lblLogin.Location = new System.Drawing.Point(12, 126);
            this.lblLogin.Name = "lblLogin";
            this.lblLogin.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.lblLogin.Size = new System.Drawing.Size(257, 17);
            this.lblLogin.TabIndex = 5;
            this.lblLogin.Text = "SQL имя пользователя:";
            // 
            // lblPassword
            // 
            this.lblPassword.BackColor = System.Drawing.SystemColors.Control;
            this.lblPassword.Cursor = System.Windows.Forms.Cursors.Default;
            this.lblPassword.ForeColor = System.Drawing.SystemColors.ControlText;
            this.lblPassword.Location = new System.Drawing.Point(12, 168);
            this.lblPassword.Name = "lblPassword";
            this.lblPassword.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.lblPassword.Size = new System.Drawing.Size(257, 17);
            this.lblPassword.TabIndex = 7;
            this.lblPassword.Text = "SQL пароль:";
            // 
            // lblDatabase
            // 
            this.lblDatabase.BackColor = System.Drawing.SystemColors.Control;
            this.lblDatabase.Cursor = System.Windows.Forms.Cursors.Default;
            this.lblDatabase.ForeColor = System.Drawing.SystemColors.ControlText;
            this.lblDatabase.Location = new System.Drawing.Point(12, 87);
            this.lblDatabase.Name = "lblDatabase";
            this.lblDatabase.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.lblDatabase.Size = new System.Drawing.Size(257, 17);
            this.lblDatabase.TabIndex = 2;
            this.lblDatabase.Text = "База данных:";
            // 
            // Label2
            // 
            this.Label2.BackColor = System.Drawing.SystemColors.Control;
            this.Label2.Cursor = System.Windows.Forms.Cursors.Default;
            this.Label2.ForeColor = System.Drawing.SystemColors.ControlText;
            this.Label2.Location = new System.Drawing.Point(16, 308);
            this.Label2.Name = "Label2";
            this.Label2.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.Label2.Size = new System.Drawing.Size(121, 17);
            this.Label2.TabIndex = 13;
            this.Label2.Text = "Ошибки";
            // 
            // lblmsg
            // 
            this.lblmsg.Location = new System.Drawing.Point(280, 311);
            this.lblmsg.Name = "lblmsg";
            this.lblmsg.Size = new System.Drawing.Size(272, 13);
            this.lblmsg.TabIndex = 18;
            this.lblmsg.Click += new System.EventHandler(this.lblmsg_Click);
            // 
            // cmdConnect
            // 
            this.cmdConnect.BackColor = System.Drawing.SystemColors.Control;
            this.cmdConnect.Cursor = System.Windows.Forms.Cursors.Default;
            this.cmdConnect.ForeColor = System.Drawing.SystemColors.ControlText;
            this.cmdConnect.Location = new System.Drawing.Point(12, 221);
            this.cmdConnect.Name = "cmdConnect";
            this.cmdConnect.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.cmdConnect.Size = new System.Drawing.Size(256, 25);
            this.cmdConnect.TabIndex = 19;
            this.cmdConnect.Text = "Connect";
            this.cmdConnect.UseVisualStyleBackColor = false;
            this.cmdConnect.Click += new System.EventHandler(this.cmdConnect_Click);
            // 
            // frmPGImport
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.Control;
            this.ClientSize = new System.Drawing.Size(562, 410);
            this.Controls.Add(this.cmdConnect);
            this.Controls.Add(this.lblmsg);
            this.Controls.Add(this.Frame1);
            this.Controls.Add(this.txtLog);
            this.Controls.Add(this.cmdGo);
            this.Controls.Add(this.frameRight);
            this.Controls.Add(this.Label2);
            this.Cursor = System.Windows.Forms.Cursors.Default;
            this.Location = new System.Drawing.Point(356, 40);
            this.Name = "frmPGImport";
            this.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Инсталлятор базы данных  Postgre SQL";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.Frame1.ResumeLayout(false);
            this.frameRight.ResumeLayout(false);
            this.frameRight.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }
        internal Label lblmsg;
        public Label Label4;
        public TextBox txtPort;
        #endregion

        public Button cmdConnect;
        public TextBox txtServer;
    }
}