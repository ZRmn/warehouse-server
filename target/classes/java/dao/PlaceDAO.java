package dao;

import models.Box;
import models.Place;
import models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import utils.IdSeeker;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.*;

@Component
public class PlaceDAO implements CrudDAO<Place>
{
    private JdbcTemplate template;
    private Map<Integer, Place> placesMap;

    private RowMapper<Place> placeRowMapper = (resultSet, i) ->
    {
        Integer placeId = resultSet.getInt("placeId");

        if (!placesMap.containsKey(placeId))
        {
            String position = resultSet.getString("position");
            Integer capacity = resultSet.getInt("capacity");
            Integer fullness = resultSet.getInt("fullness");


            Place place = new Place(placeId, position, capacity, fullness, new ArrayList<>());

            placesMap.put(placeId, place);
        }

        Integer boxId = resultSet.getInt("boxId");

        if (boxId != 0)
        {
            Integer productsCount = resultSet.getInt("products_count");
            Integer productId = resultSet.getInt("productId");
            Long code = resultSet.getLong("code");
            String description = resultSet.getString("description");

            Product product = new Product(productId, code, description);
            Box box = new Box(boxId, productsCount, product);

            placesMap.get(placeId).getBoxes().add(box);
        }

        return placesMap.get(placeId);
    };

    @Autowired
    public PlaceDAO(DataSource dataSource)
    {
        template = new JdbcTemplate(dataSource);
        placesMap = new HashMap<>();
    }

    @Override
    public void create(Place model)
    {
        String query = "INSERT INTO warehouse.place (id, position, capacity, fullness) VALUES(?, ?, ?, ?)";
        Object[] args = {findId(), model.getPosition(), model.getCapacity(), model.getFullness()};
        int[] types = {Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.INTEGER};

        template.update(query, args, types);
    }

    @Override
    public Place retrieve(Place model)
    {
        String query = "SELECT place.id AS placeId, box.id AS boxId, product.id AS productId, * FROM warehouse.place LEFT JOIN warehouse.box ON place.id = box.place_id LEFT JOIN warehouse.product ON box.product_id = product.id WHERE place.id = ?";
        Object[] args = {model.getId()};
        int[] types = {Types.INTEGER};

        template.query(query, args, types, placeRowMapper);

        List<Place> places = new ArrayList<>(placesMap.values());

        placesMap.clear();

        return places.isEmpty() ? null : places.get(0);
    }

    @Override
    public List<Place> retrieveAll()
    {
        String query = "SELECT place.id AS placeId, box.id AS boxId, product.id AS productId, * FROM warehouse.place LEFT JOIN warehouse.box ON place.id = box.place_id LEFT JOIN warehouse.product ON box.product_id = product.id";
        template.query(query, placeRowMapper);
        List<Place> places = new ArrayList<>(placesMap.values());

        placesMap.clear();

        return places;
    }

    @Override
    public void update(Place model)
    {
        String query = "UPDATE warehouse.place SET position = ?, capacity = ?, fullness = ? WHERE id = ?";
        Object[] args = {model.getPosition(), model.getCapacity(), model.getFullness(), model.getId()};
        int[] types = {Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER};

        template.update(query, args, types);
    }

    @Override
    public void delete(Place model)
    {
        String query = "DELETE FROM warehouse.place WHERE id = ?";
        Object[] args = {model.getId()};
        int[] types = {Types.INTEGER};

        template.update(query, args, types);
    }

    @Override
    public void deleteAll()
    {

    }

    private RowMapper<Place> simplePlaceRowMapper = (resultSet, i) ->
    {
        Integer id = resultSet.getInt("id");
        String pos = resultSet.getString("position");
        Integer capacity = resultSet.getInt("capacity");
        Integer fullness = resultSet.getInt("fullness");

        return new Place(id, pos, capacity, fullness, new ArrayList<>());
    };

    public Place getPlaceByPosition(String position)
    {
        String query = "SELECT * FROM warehouse.place WHERE position = ?";
        Object[] args = {position};
        int[] types = {Types.VARCHAR};

        List<Place> places = template.query(query, args, types, simplePlaceRowMapper);

        return places.isEmpty() ? null : places.get(0);
    }

    private Integer findId()
    {
        String query = "SELECT id FROM warehouse.place";

        List<Integer> ids = template.query(query, (resultSet, i) -> resultSet.getInt("id"));

        return IdSeeker.find(ids.toArray(new Integer[0]));
    }
}
