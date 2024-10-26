package mvc.codejava.service;

import java.util.List;

import mvc.codejava.entity.Room;
import mvc.codejava.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
	@Autowired
	private RoomRepository roomRepository;
	

	public List<Room> list(){
		return (List<Room>) roomRepository.findAll();
	}
	
	public void save(Room room) {
		roomRepository.save(room);
	}
	
	public Room get(Long id) {
		return roomRepository.findById(id).get();
	}
	
	public void delete(Long id) {
		roomRepository.deleteById(id);
	}
}
