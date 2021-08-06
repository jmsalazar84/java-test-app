package cl.vgl.movies;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MovieService {

    private static MovieService instance;

    private ArrayList<Movie> movies = new ArrayList<Movie>();

    private MovieService() {
        this.init();
    }

    private void init() {
        JSONParser parser = new JSONParser();

        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator
                + "assets";

        try (FileReader reader = new FileReader(path + "/movies.json")) {
            JSONArray list = (JSONArray) parser.parse(reader);
            for (Object dto : list) {
                Movie movie = this.mapToDomain(dto);
                this.movies.add(movie);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private Movie mapToDomain(Object object) {
        JSONObject dto = (JSONObject) object;
        Movie movie = new Movie();
        String title = (String) dto.get("title");
        movie.setTitle(title);
        return movie;
    }

    public static MovieService getInstance() {
        if (instance == null) {
            instance = new MovieService();
        }
        return instance;
    }

    public ArrayList<Movie> getMovies() {
        return this.movies;
    }

}
