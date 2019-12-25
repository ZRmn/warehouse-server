package dao;

import models.Box;
import models.Pallet;
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
        Integer placeId = resultSet.getInt("place_id");

        if (!placesMap.containsKey(placeId))
        {
            String position = resultSet.getString("position");
            Integer capacity = resultSet.getInt("capacity");
            Integer fullness = resultSet.getInt("fullness");


            Place place = new Place(placeId, position, capacity, fullness, new ArrayList<>());

            placesMap.put(placeId, place);
        }

        Integer palletId = resultSet.getInt("palletId");

        if (palletId != 0)
        {
            Integer count = resultSet.getInt("count");
            Integer boxId = resultSet.getInt("boxId");
            Integer productsCount = resultSet.getInt("products_count");
            Integer productId = resultSet.getInt("productId");
            Long code = resultSet.getLong("code");
            String description = resultSet.getString("description");

            Product product = new Product(productId, code, description);
            Box box = new Box(boxId, productsCount, product);
            Pallet pallet = new Pallet(palletId, box, count);

            placesMap.get(placeId).getPallets().add(pallet);
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
        int id = findId();

        int fullness = 0;

        for (Pallet pallet : model.getPallets())
        {
            fullness += pallet.getCount();
        }

        for (Pallet pallet : model.getPallets())
        {
            int palletId = findPalletId();

            String palletQuery = "INSERT INTO warehouse.pallet (id, box_id, count) values (?, ?, ?)";
            Object[] palletArgs = {palletId, pallet.getBox().getId(), pallet.getCount()};
            int[] palletTypes = {Types.INTEGER, Types.INTEGER, Types.INTEGER};
            template.update(palletQuery, palletArgs, palletTypes);


            String query = "INSERT INTO warehouse.place (position, capacity, fullness, place_id, pallet_id) VALUES(?, ?, ?, ?, ?)";
            Object[] args = {model.getPosition(), model.getCapacity(), fullness, id, palletId};
            int[] types = {Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER};
            template.update(query, args, types);
        }
    }

    @Override
    public Place retrieve(Place model)
    {
        return null;
    }

    @Override
    public List<Place> retrieveAll()
    {
        String query = "SELECT warehouse.pallet.id AS palletId, box.id AS boxId, product.id AS productId, * FROM warehouse.place LEFT JOIN warehouse.pallet ON place.pallet_id = pallet.id LEFT JOIN warehouse.box ON warehouse.pallet.box_id = box.id LEFT JOIN warehouse.product ON box.product_id = product.id";
        template.query(query, placeRowMapper);
        List<Place> places = new ArrayList<>(placesMap.values());

        placesMap.clear();

        return places;
    }

    @Override
    public void update(Place model)
    {

    }

    @Override
    public void delete(Place model)
    {
        String query = "DELETE FROM warehouse.place WHERE place_id = ?";
        Object[] args = {model.getId()};
        int[] types = {Types.INTEGER};

        template.update(query, args, types);
    }

    @Override
    public void deleteAll()
    {

    }

    public void setCapacity(Integer capacity)
    {
        String query = "UPDATE warehouse.place SET capacity=?";
        Object[] args = {capacity};
        int[] types = {Types.INTEGER};
        template.update(query, args, types);
    }


    public Place getPlaceByPosition(String position)
    {
        String query = "SELECT warehouse.pallet.id AS palletId, box.id AS boxId, product.id AS productId, * FROM warehouse.place LEFT JOIN warehouse.pallet ON place.pallet_id = pallet.id LEFT JOIN warehouse.box ON warehouse.pallet.box_id = box.id LEFT JOIN warehouse.product ON box.product_id = product.id WHERE position = ?";
        Object[] args = {position};
        int[] types = {Types.VARCHAR};

        template.query(query, args, types, placeRowMapper);

        List<Place> places = new ArrayList<>(placesMap.values());

        placesMap.clear();

        return places.isEmpty() ? null : places.get(0);
    }

    private Integer findId()
    {
        String query = "SELECT place_id FROM warehouse.place";

        List<Integer> ids = template.query(query, (resultSet, i) -> resultSet.getInt("place_id"));

        return IdSeeker.find(ids.toArray(new Integer[0]));
    }

    private Integer findPalletId()
    {
        String query = "SELECT id FROM warehouse.pallet";

        List<Integer> ids = template.query(query, (resultSet, i) -> resultSet.getInt("id"));

        return IdSeeker.find(ids.toArray(new Integer[0]));
    }
}
