package rc.dbseeder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import rc.document.Address;
import rc.document.Hotel;
import rc.document.Review;
import rc.repository.HotelRepository;

@Component
public class DbSeeder implements CommandLineRunner{
	
	private HotelRepository hotelRepository;
	
	public DbSeeder(HotelRepository hotelRepository) {
		this.hotelRepository = hotelRepository;
	}

	@Override
	public void run(String...strings) throws Exception{
		
		Hotel marriot = new Hotel(
				"Marriot",
				130,
				new Address("Paris","France"),
				Arrays.asList(
						new Review("John",8,false),
						new Review("MAry",7,true)
						)
				);
		
		Hotel ibis = new Hotel(
				"Ibis",
				90,
				new Address("Bucharest","Romania"),
				Arrays.asList(
						new Review("Teddy",9,true)
						)
				);
	
		Hotel sofitel = new Hotel(
				"Sofitel",
				200,
				new Address("Rome","Italy"),
				new ArrayList()
				);
		
		Hotel charrua = new Hotel(
				"Charrua",
				130,
				new Address("Santa Cruz do Sul","Brazil"),
				Arrays.asList(
						new Review("Joao",8,false),
						new Review("Maria",7,true)
						)
				);		
		Hotel coliseu = new Hotel(
				"Coliseu",
				110,
				new Address("Porto Alegre","Brazil"),
				Arrays.asList(
						new Review("Carlos",8,false),
						new Review("Carol",7,true)
						)
				);			
		hotelRepository.deleteAll();
	
		
		List <Hotel> hotels = Arrays.asList(marriot,ibis,sofitel,charrua,coliseu);
		this.hotelRepository.insert(hotels);
		
	}

}
