using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Worker
{
    public partial class Form1 : Form
    {

        public Form1()
        {
            InitializeComponent();

            while(true)
            {
                UpdateDB();
            }
            
        }
        public static void UpdateDB ()
        {
            bool Status;
            Random random = new Random();
            string Id = "1";

            Status = random.Next(2) == 0 ? true : false;

            var json = Newtonsoft.Json.JsonConvert.SerializeObject(new
            {
                Name = Id,
                Value = Status,

            });
    

            var request = WebRequest.CreateHttp("https://parkingdb-a7779.firebaseio.com/Parcare1/.json");

            request.Method = "PATCH";
            request.ContentType = "application/json";
            var buffer = Encoding.UTF8.GetBytes(json);
            request.ContentLength = buffer.Length;
            request.GetRequestStream().Write(buffer, 0, buffer.Length);
            var response = request.GetResponse();
            json = (new StreamReader(response.GetResponseStream())).ReadToEnd();
        }
   
    }
}
