using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using WebApplication1.Models;


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
        public IEnumerable<ParkDetails> GetParkDet()
        {
            return _context.ParkDet;
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
        public async Task<IActionResult> PutParkDetails([FromBody] ParkDetails parkDetails)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            //if (id != parkDetails.Id)
            //{
            //    return BadRequest();
            //}

            _context.Entry(parkDetails).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ParkDetailsExists(parkDetails.Id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
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