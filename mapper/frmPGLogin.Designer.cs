using System;
using System.Diagnostics;
using System.Drawing;
using System.Windows.Forms;

namespace mapper
{
    
    internal partial class frmPGLogin
    {
        #region Windows Form Designer generated code 
        
        public frmPGLogin() : base()
        {
            // This call is required by the Windows Form Designer.
            InitializeComponent();
        }
        
        // Required by the Windows Form Designer
        private System.ComponentModel.IContainer components;
        public ToolTip ToolTip1;
        public TextBox txtLogin;
        public TextBox txtPassword;
        public TextBox txtDatabase;
        public Label lblServer;
        public Label lblLogin;
        public Label lblPassword;
        public Label lblDatabase;
        public GroupBox frameRight;
        // NOTE: The following procedure is required by the Windows Form Designer
        // It can be modified using the Windows Form Designer.
        // Do not modify it using the code editor.
        [DebuggerStepThrough()]
        private void InitializeComponent()
        {
            components = new System.ComponentModel.Container();
            ToolTip1 = new ToolTip(components);
            frameRight = new GroupBox();
            txtServer = new TextBox();
            Label4 = new Label();
            txtPort = new TextBox();
            txtLogin = new TextBox();
            txtPassword = new TextBox();
            txtDatabase = new TextBox();
            lblServer = new Label();
            lblLogin = new Label();
            lblPassword = new Label();
            lblDatabase = new Label();
            lblmsg = new Label();
            cmdConnect = new Button();
            dlgSaveTo = new SaveFileDialog();
            frameRight.SuspendLayout();

            // 
            // frameRight
            // 
            frameRight.BackColor = SystemColors.Control;
            frameRight.Controls.Add(txtServer);
            frameRight.Controls.Add(Label4);
            frameRight.Controls.Add(txtPort);
            frameRight.Controls.Add(txtLogin);
            frameRight.Controls.Add(txtPassword);
            frameRight.Controls.Add(txtDatabase);
            frameRight.Controls.Add(lblServer);
            frameRight.Controls.Add(lblLogin);
            frameRight.Controls.Add(lblPassword);
            frameRight.Controls.Add(lblDatabase);
            frameRight.ForeColor = SystemColors.ControlText;
            frameRight.Location = new Point(0, 0);
            frameRight.Margin = new Padding(4, 3, 4, 3);
            frameRight.Name = "frameRight";
            frameRight.Padding = new Padding(4, 3, 4, 3);
            frameRight.RightToLeft = RightToLeft.No;
            frameRight.Size = new Size(323, 248);
            frameRight.TabIndex = 15;
            frameRight.TabStop = false;
            frameRight.Text = "Параметры подключения";
            // 
            // txtServer
            // 
            txtServer.AcceptsReturn = true;
            txtServer.BackColor = SystemColors.Window;
            txtServer.Cursor = Cursors.IBeam;
            txtServer.ForeColor = SystemColors.WindowText;
            txtServer.ImeMode = ImeMode.Disable;
            txtServer.Location = new Point(13, 37);
            txtServer.Margin = new Padding(4, 3, 4, 3);
            txtServer.MaxLength = 0;
            txtServer.Name = "txtServer";
            txtServer.RightToLeft = RightToLeft.No;
            txtServer.Size = new Size(299, 23);
            txtServer.TabIndex = 12;
            // 
            // Label4
            // 
            Label4.BackColor = SystemColors.Control;
            Label4.ForeColor = SystemColors.ControlText;
            Label4.Location = new Point(15, 77);
            Label4.Margin = new Padding(4, 0, 4, 0);
            Label4.Name = "Label4";
            Label4.RightToLeft = RightToLeft.No;
            Label4.Size = new Size(112, 20);
            Label4.TabIndex = 11;
            Label4.Text = "Порт:";
            // 
            // txtPort
            // 
            txtPort.AcceptsReturn = true;
            txtPort.BackColor = SystemColors.Window;
            txtPort.Cursor = Cursors.IBeam;
            txtPort.ForeColor = SystemColors.WindowText;
            txtPort.Location = new Point(166, 74);
            txtPort.Margin = new Padding(4, 3, 4, 3);
            txtPort.MaxLength = 0;
            txtPort.Name = "txtPort";
            txtPort.RightToLeft = RightToLeft.No;
            txtPort.Size = new Size(146, 23);
            txtPort.TabIndex = 10;
            txtPort.Text = "5432";
            // 
            // txtLogin
            // 
            txtLogin.AcceptsReturn = true;
            txtLogin.BackColor = SystemColors.Window;
            txtLogin.Cursor = Cursors.IBeam;
            txtLogin.ForeColor = SystemColors.WindowText;
            txtLogin.Location = new Point(14, 162);
            txtLogin.Margin = new Padding(4, 3, 4, 3);
            txtLogin.MaxLength = 0;
            txtLogin.Name = "txtLogin";
            txtLogin.RightToLeft = RightToLeft.No;
            txtLogin.Size = new Size(299, 23);
            txtLogin.TabIndex = 2;
            txtLogin.Text = "postgres";
            // 
            // txtPassword
            // 
            txtPassword.AcceptsReturn = true;
            txtPassword.BackColor = SystemColors.Window;
            txtPassword.Cursor = Cursors.IBeam;
            txtPassword.ForeColor = SystemColors.WindowText;
            txtPassword.ImeMode = ImeMode.Disable;
            txtPassword.Location = new Point(14, 211);
            txtPassword.Margin = new Padding(4, 3, 4, 3);
            txtPassword.MaxLength = 0;
            txtPassword.Name = "txtPassword";
            txtPassword.PasswordChar = '*';
            txtPassword.RightToLeft = RightToLeft.No;
            txtPassword.Size = new Size(299, 23);
            txtPassword.TabIndex = 3;
            // 
            // txtDatabase
            // 
            txtDatabase.AcceptsReturn = true;
            txtDatabase.BackColor = SystemColors.Window;
            txtDatabase.Cursor = Cursors.IBeam;
            txtDatabase.ForeColor = SystemColors.WindowText;
            txtDatabase.Location = new Point(14, 119);
            txtDatabase.Margin = new Padding(4, 3, 4, 3);
            txtDatabase.MaxLength = 0;
            txtDatabase.Name = "txtDatabase";
            txtDatabase.RightToLeft = RightToLeft.No;
            txtDatabase.Size = new Size(299, 23);
            txtDatabase.TabIndex = 1;
            // 
            // lblServer
            // 
            lblServer.BackColor = SystemColors.Control;
            lblServer.ForeColor = SystemColors.ControlText;
            lblServer.Location = new Point(14, 18);
            lblServer.Margin = new Padding(4, 0, 4, 0);
            lblServer.Name = "lblServer";
            lblServer.RightToLeft = RightToLeft.No;
            lblServer.Size = new Size(300, 20);
            lblServer.TabIndex = 0;
            lblServer.Text = "Postgre SQL сервер:";
            // 
            // lblLogin
            // 
            lblLogin.BackColor = SystemColors.Control;
            lblLogin.ForeColor = SystemColors.ControlText;
            lblLogin.Location = new Point(14, 145);
            lblLogin.Margin = new Padding(4, 0, 4, 0);
            lblLogin.Name = "lblLogin";
            lblLogin.RightToLeft = RightToLeft.No;
            lblLogin.Size = new Size(300, 20);
            lblLogin.TabIndex = 5;
            lblLogin.Text = "SQL имя пользователя:";
            // 
            // lblPassword
            // 
            lblPassword.BackColor = SystemColors.Control;
            lblPassword.ForeColor = SystemColors.ControlText;
            lblPassword.Location = new Point(14, 194);
            lblPassword.Margin = new Padding(4, 0, 4, 0);
            lblPassword.Name = "lblPassword";
            lblPassword.RightToLeft = RightToLeft.No;
            lblPassword.Size = new Size(300, 20);
            lblPassword.TabIndex = 7;
            lblPassword.Text = "SQL пароль:";
            // 
            // lblDatabase
            // 
            lblDatabase.BackColor = SystemColors.Control;
            lblDatabase.ForeColor = SystemColors.ControlText;
            lblDatabase.Location = new Point(14, 100);
            lblDatabase.Margin = new Padding(4, 0, 4, 0);
            lblDatabase.Name = "lblDatabase";
            lblDatabase.RightToLeft = RightToLeft.No;
            lblDatabase.Size = new Size(300, 20);
            lblDatabase.TabIndex = 2;
            lblDatabase.Text = "База данных:";
            // 
            // lblmsg
            // 
            lblmsg.Location = new Point(327, 359);
            lblmsg.Margin = new Padding(4, 0, 4, 0);
            lblmsg.Name = "lblmsg";
            lblmsg.Size = new Size(317, 15);
            lblmsg.TabIndex = 18;
            lblmsg.Click += lblmsg_Click;
            // 
            // cmdConnect
            // 
            cmdConnect.BackColor = SystemColors.Control;
            cmdConnect.ForeColor = SystemColors.ControlText;
            cmdConnect.Location = new Point(14, 255);
            cmdConnect.Margin = new Padding(4, 3, 4, 3);
            cmdConnect.Name = "cmdConnect";
            cmdConnect.RightToLeft = RightToLeft.No;
            cmdConnect.Size = new Size(299, 29);
            cmdConnect.TabIndex = 19;
            cmdConnect.Text = "Connect";
            cmdConnect.UseVisualStyleBackColor = false;
            cmdConnect.Click += cmdConnect_Click;
            // 
            // dlgSaveTo
            // 
            dlgSaveTo.Filter = "xml|*.xml|all|*.*";
            dlgSaveTo.Title = "Import to";
            // 
            // frmPGLogin
            // 
            AutoScaleDimensions = new SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            BackColor = SystemColors.Control;
            ClientSize = new Size(333, 307);
            Controls.Add(cmdConnect);
            Controls.Add(lblmsg);
            Controls.Add(frameRight);
            FormBorderStyle = FormBorderStyle.FixedToolWindow;
            Location = new Point(356, 40);
            Margin = new Padding(4, 3, 4, 3);
            Name = "frmPGLogin";
            RightToLeft = RightToLeft.No;
            StartPosition = FormStartPosition.CenterScreen;
            Text = "Соединение с базой данных  Postgre SQL";
            Load += Form1_Load;
            frameRight.ResumeLayout(false);
            frameRight.PerformLayout();
            

        }
        internal Label lblmsg;
        public Label Label4;
        public TextBox txtPort;
        #endregion

        public Button cmdConnect;
        public TextBox txtServer;
        private SaveFileDialog dlgSaveTo;
    }
}