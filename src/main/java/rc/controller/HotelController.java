package rc.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.dsl.BooleanExpression;

import rc.document.Hotel;
import rc.document.QHotel;
import rc.repository.HotelRepository;

@RestController
@RequestMapping("/hotels")
public class HotelController {
	
	private HotelRepository hotelRepository;

	public HotelController(HotelRepository hotelRepository) {
		this.hotelRepository = hotelRepository;
	}

	@GetMapping("/all")
	public List<Hotel> getAll(){
		List<Hotel> hotels = this.hotelRepository.findAll();
		return hotels;
	}
	
	@PutMapping
	public void insert(@RequestBody Hotel hotel) {
		this.hotelRepository.insert(hotel);		
	}
	
	@PostMapping
	public void update(@RequestBody Hotel hotel) {
		this.hotelRepository.save(hotel);
	}

	@DeleteMapping ("/{id}")
	public void delete (@PathVariable("id") String id) {
		this.hotelRepository.deleteById(id);
	}

	@GetMapping("/{id}")
	public Hotel getById(@PathVariable("id") String id) {
		Optional<Hotel> hotel = this.hotelRepository.findById(id);
		return hotel.get();
	}
	
	@GetMapping("/price/{maxPrice}")
	public List<Hotel> getByPricePerNight(@PathVariable("maxPrice") int maxPrice){
		List<Hotel> hotels= this.hotelRepository.findByPricePerNightLessThan(maxPrice);
		return hotels;
		
	}
	
	@GetMapping("/address/{city}")
	public List<Hotel> getByCity(@PathVariable("city") String city){
		List<Hotel> hotels= this.hotelRepository.findByCity(city);
		return hotels;
		
	}
	
	@GetMapping("/country/{country}")
	public List<Hotel> getByCountry(@PathVariable("country") String country){
		// create a query class
		QHotel qHotel = new QHotel("hotel");
		
		// create filter
		BooleanExpression filterByCountry = qHotel.address.country.eq(country);
		
		//pass the filter into the repo
		List<Hotel> hotels = (List<Hotel>) this.hotelRepository.findAll(filterByCountry);
		return hotels;
		
	}	
	
	@GetMapping("/recommended")
	public List<Hotel> getRecommended(){
		final int maxPrice =131;
		final int minRating = 7;
		
		// create a query class
		QHotel qHotel = new QHotel("hotel");
		
		// create filter
		BooleanExpression filterByMaxPrice = qHotel.pricePerNight.lt(maxPrice);
		BooleanExpression filterByMinRating = qHotel.reviews.any().rating.gt(minRating);
		
		
		//pass the filter into the repo
		List<Hotel> hotels = (List<Hotel>) this.hotelRepository.findAll(filterByMaxPrice.and(filterByMinRating));
		return hotels;
		
	}

	@GetMapping("/recommended/brazil")
	public List<Hotel> getRecommendedBrazil(){
		final int maxPrice =131;
		final int minRating = 7;
		
		// create a query class
		QHotel qHotel = new QHotel("hotel");
		
		// create filter
		BooleanExpression filterByMaxPrice = qHotel.pricePerNight.lt(maxPrice);
		BooleanExpression filterByMinRating = qHotel.reviews.any().rating.gt(minRating);
		BooleanExpression filterByCountry = qHotel.address.country.equalsIgnoreCase("brazil");
		
		
		//pass the filter into the repo
		List<Hotel> hotels = (List<Hotel>) this.hotelRepository.findAll(filterByMaxPrice
				.and(filterByMinRating)
				.and(filterByCountry)
				.or(qHotel.address.country.equalsIgnoreCase("ROMANIA"))
				);
		return hotels;
		
	}
	
	
}
