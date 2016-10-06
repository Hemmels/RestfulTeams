package pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.annotations.VisibleForTesting;

import data.DataMapper;
import lombok.Data;

@Data
public class FootballTeam implements Serializable {

	private static final long serialVersionUID = 2609951496647053136L;
	
	@VisibleForTesting
	public static final FootballTeam SALISBURY_FC = DataMapper.createFootballTeam
			("Salisbury FC", "Salisbury", "Matt Grigsby", 6000, "Non League", 22, new Date(1397257200000L));

	private String name;
	private String city;
	private String owner;
	private int stadiumCapacity;
	private String competition;
	private int numberOfPlayers;
	private Date dateOfCreation;

	@JsonIgnore
	public boolean isValid() {
		// A very basic entry must have a name, city and creation date.
		return name != null && !name.isEmpty() &&
				city != null && !city.isEmpty() &&
				dateOfCreation != null && dateOfCreation.before(new Date());
	}
	
}
