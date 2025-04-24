using System;
using System.Diagnostics;
using System.IO;
using System.Text;
using System.Windows.Forms;
using System.Data;
using System.Reflection;
//using dv21_util;
using System.Security.AccessControl;
using System.Collections.Generic;
using System.ComponentModel.Design.Serialization;


namespace mapper
{

    internal partial class frmPGLogin : Form
    {
        private StringBuilder sOut;
        private string Path;
        private int iListIndex;
        private bool bDontClear;
        public pgDataSource DS;
        private string Log;


        private string n;
        private int cnt;
        private object i;
        private bool inClick;







     

       



        



        private void Form1_Load(object sender, EventArgs e)
        {
            txtServer.Text =  "localhost";
            

        }

        private void lblmsg_Click(object sender, EventArgs e)
        {

        }

     

        private void cmdConnect_Click(object sender, EventArgs e)
        {

            try
            {

            
            DS = new pgDataSource();
            DS.Server = txtServer.Text;
            DS.DataBaseName = txtDatabase.Text;
            DS.UserName = txtLogin.Text;
            DS.Password = txtPassword.Text;
            DS.Port = txtPort.Text;
            DS.Integrated = false;
                if (!DS.ServerLogIn())
                {
                    MessageBox.Show("Не удается подключиться к PgSQL");

                    DS = null;
                    this.Close();
                    return;
                }
                else
                {

                    //DataTable shemas = DS.ReadData("SELECT schema_name FROM information_schema.schemata s  where catalog_name = '"+ DS.DataBaseName + "'");

                    frmMapper m = new frmMapper();
                    m.DS = DS;
                    m.Show();
                    m.Init();
                    this.Hide();

                }


            } catch (System.Exception ex)
            {
                MessageBox.Show(ex.Message);
                this.Close();
                return;
            }


        }

       

       
    }
}