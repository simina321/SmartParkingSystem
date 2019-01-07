using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using WebApplication1.Models;
using System.Net;
using Newtonsoft.Json.Linq;
using System.IO;
using System.Text;

namespace WebApplication1.Controllers
{
    [Produces("application/json")]
    [Route("api/PD")]
    public class PDController : Controller
    {
        private readonly ToDoContext _context;

        public PDController(ToDoContext context)
        {
            _context = context;
        }

        // GET: api/PD
        [HttpGet]
        public JObject GetParkDet()
        {
            /***********************************************************/
            string URL = "https://parkingdb-a7779.firebaseio.com/Parcare1/.json";
            var HTTPrequest = (HttpWebRequest)WebRequest.Create(URL);
            var Response = (HttpWebResponse)HTTPrequest.GetResponse();
            var StreamRead = new StreamReader(Response.GetResponseStream()).ReadToEnd();
            var Data = JObject.Parse(StreamRead);

            return Data;

            /**********************************************************/
            //   return _context.ParkDet;
        }

        // GET: api/PD/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetParkDetails([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var parkDetails = await _context.ParkDet.SingleOrDefaultAsync(m => m.Id == id);

            if (parkDetails == null)
            {
                return NotFound();
            }

            return Ok(parkDetails);
        }

        // PUT: api/PD
        [HttpPut]
        public void PutParkDetails([FromBody] ParkDetails parkDetails)
        {
       
            var json = Newtonsoft.Json.JsonConvert.SerializeObject(new
            {
                Name = parkDetails.Id,
                Value = parkDetails.Status,

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

        // POST: api/PD
        [HttpPost]
        public async Task<IActionResult> PostParkDetails([FromBody] ParkDetails parkDetails)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }


            _context.ParkDet.Add(parkDetails);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetParkDetails", new { id = parkDetails.Id }, parkDetails);

        }

        // DELETE: api/PD/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteParkDetails([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var parkDetails = await _context.ParkDet.SingleOrDefaultAsync(m => m.Id == id);
            if (parkDetails == null)
            {
                return NotFound();
            }

            _context.ParkDet.Remove(parkDetails);
            await _context.SaveChangesAsync();

            return Ok(parkDetails);
        }

        private bool ParkDetailsExists(int id)
        {
            return _context.ParkDet.Any(e => e.Id == id);
        }
    }
}