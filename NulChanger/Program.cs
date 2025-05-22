using System;
using System.IO;
using System.Linq;
using System.Text;
using System.Collections.Generic;



namespace NulChanger
{
    internal class Program
    {
        static void Main(string[] args)
        {

			if (args.Length < 1)
			{
				Console.WriteLine("Usage: NulChanger sourceFolderPath");
				return;
			}
			string source = args[0];
			Encoding fileEncoding = Encoding.GetEncoding(1251);

			var files = Directory.EnumerateFiles(source, "*.csv", SearchOption.TopDirectoryOnly);


			foreach (string fileName in files)
			{
				Console.WriteLine(fileName);
				String contents = File.ReadAllText(fileName, fileEncoding);
				File.WriteAllText(fileName, contents.Replace('\0', ' '), fileEncoding);
				
			}

			Console.WriteLine("Done");


		}
    }
}
