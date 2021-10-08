package com.company;
/** A  program for finding a an artist song or album from a database of artist collections
 * @author Felix Ogbonnaya
 * @since 2020-01-06
 */
import com.company.model.Artist;
import com.company.model.Datasource;
import com.company.model.SongArtist;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Datasource datasource = new Datasource();

        if (!datasource.open()) {
            System.out.println("Can't open datasource");
            return;
        }

        List<Artist> artists = datasource.queryArtist(datasource.ORDER_BY_NONE);
        if (artists == null) {
            System.out.println("No artist");
            return;
        }
        for (Artist artist : artists) {
            System.out.println("ID = " + artist.getId() + ", Name= " + artist.getName());
        }

        List<String> albumsForArtist =
                datasource.queryAlbumsForArtist("Iron Maiden", Datasource.ORDER_BY_ASC);

        for (String album : albumsForArtist) {
            System.out.println(album);
        }

        List<SongArtist> songArtists = datasource.queryArtistsForSong("Heartless", Datasource.ORDER_BY_ASC);

        if (songArtists == null) {
            System.out.println("Could't find the artists for the song");
        }

        for (SongArtist artist : songArtists) {
            System.out.println("Artist name = " + artist.getArtistName() +
                    " Album name = " + artist.getAlbumName() +
                    " Track = " + artist.getTrack());
        }

        datasource.querySongsMetaData();

        int count = datasource.getCount(Datasource.TABLE_SONGS);
        System.out.println("Number of songs is "+ count);

        datasource.createViewForSongArtists();

        Scanner input = new Scanner(System.in);
        System.out.println("Enter a song title: ");
        String title = input.nextLine();

        songArtists = datasource.querySongInfoView(title);

        if(songArtists.isEmpty()){
            System.out.println("Couldn't find the artist for the song");
            return;
        }

        for(SongArtist artist :songArtists){
            System.out.println("From View - Artist name = "+ artist.getArtistName() +
                    " Album name = " + artist.getAlbumName() +
                    " Track number = "+ artist.getTrack());
        }
        datasource.close();
    }
}
