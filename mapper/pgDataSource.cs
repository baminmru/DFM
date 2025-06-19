using System;
using System.Data;
using System.Data.Common;
using System.Diagnostics;
using Microsoft.VisualBasic;
using Npgsql;
using NpgsqlTypes;


namespace mapper
{



    public class pgDataSource
    {
        
        private NpgsqlConnection mPgSQLConnection;
        private NpgsqlDataAdapter mDA;

        private bool InTransaction;
        private string mPrevConnectionString;
        private string mPrevProvider;
        private int mPrevTimeOut;

        public string Server;
        public string DataBaseName;
        public string UserName;
        public string Password;
        public string Port;
        public bool Integrated;
        private string lMsg;

        public string LastMessage()
        {
            return default;

        }

     
        ~pgDataSource()
        {
            CloseClass();
        }

        
        public pgDataSource() : base()
        {
            mPgSQLConnection = null;
        }

        // "Деструктор"
        internal void CloseClass()
        {
            ;

            mPgSQLConnection = null;
        }

        public virtual DataTable ReadData(string SqlString)
        {
            DataTable dt = new DataTable();
            NpgsqlCommand cmd = new NpgsqlCommand(SqlString, mPgSQLConnection);
            mDA = new NpgsqlDataAdapter();
            mDA.SelectCommand = cmd;
            mDA.Fill(dt);
            mDA.Dispose();
            cmd.Dispose();
            return dt;
        }

        public bool Execute(string SqlString)
        {
            if (mPgSQLConnection is null)
                ServerLogIn();
            if (mPgSQLConnection.State != ConnectionState.Open)
                ServerLogIn();
            if (mPgSQLConnection.State != ConnectionState.Open)
                return false;

            lMsg = "";
            Debug.Print(SqlString);
            DbCommand dbcom;
            dbcom = mPgSQLConnection.CreateCommand();
            dbcom.CommandTimeout = 100;
            dbcom.CommandText = SqlString;

            try
            {
                dbcom.ExecuteNonQuery();
            }
            catch (Exception ex)
            {

                return false;
            }


            return true;

        }



        // есть ли активные транзакции
        public bool IsInTransaction()
        {
            bool IsInTransactionRet = default;
            IsInTransactionRet = InTransaction;
            return IsInTransactionRet;
        }

        public bool ServerLogIn()
        {

            if (PgSQLLogin(Server, DataBaseName, UserName, Password, 100, Port))
            {
                lMsg = "log in ok";
            }
            else
            {
                lMsg = "log in error";
            }

            return mPgSQLConnection != null;
        }


        public bool PgSQLLogin(string Server, string DataBaseName, string UserName, string Password, short aLoginTimeOut, string Port)
        {
            bool PgSQLLoginRet = default;
            try
            {
                PgSQLLoginRet = false;



                mPgSQLConnection = new NpgsqlConnection();



                string connStr;
                // Server = 127.0.0.1;Port=5432;Database=myDataBase;User Id=myUsername;Password=myPassword;

                connStr = string.Format("server={0}; user id={1}; password={2}; database={3}; Port={4}; Timeout={5}", Server, UserName, Password, DataBaseName, Port, aLoginTimeOut);

                mPgSQLConnection.ConnectionString = connStr;


                mPgSQLConnection.Open();
                PgSQLLoginRet = mPgSQLConnection.State == ConnectionState.Open; 


                return PgSQLLoginRet;
            }
            catch
            {

                return false;
            }
           
        }


        private void PgSQLLogOff()
        {

            mPgSQLConnection.Close();

            mPgSQLConnection = null;
            lMsg = "Connection closed";

        }
    }
}