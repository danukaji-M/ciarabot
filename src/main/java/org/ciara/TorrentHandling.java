package org.ciara;

import com.frostwire.jlibtorrent.AlertListener;
import com.frostwire.jlibtorrent.LibTorrent;
import com.frostwire.jlibtorrent.SessionManager;
import com.frostwire.jlibtorrent.TorrentInfo;
import com.frostwire.jlibtorrent.alerts.AddTorrentAlert;
import com.frostwire.jlibtorrent.alerts.Alert;
import com.frostwire.jlibtorrent.alerts.AlertType;
import com.frostwire.jlibtorrent.alerts.BlockFinishedAlert;

import java.io.File;

import java.util.concurrent.CountDownLatch;


public class TorrentHandling {
    public void TorrentDownload(String F_path){


        File torrentFile = new File(F_path);

        System.out.println("Using libtorrent version: " + LibTorrent.version());

        final SessionManager s = new SessionManager();

        final CountDownLatch signal = new CountDownLatch(1);

        s.addListener(new AlertListener() {
            @Override
            public int[] types() {
                return null;
            }

            @Override
            public void alert(Alert<?> alert) {
                AlertType type = alert.type();

                switch (type) {
                    case ADD_TORRENT:
                        System.out.println("Torrent added");
                        ((AddTorrentAlert) alert).handle().resume();
                        break;
                    case BLOCK_FINISHED:
                        BlockFinishedAlert a = (BlockFinishedAlert) alert;
                        int p = (int) (a.handle().status().progress() * 100);
                        System.out.println("Progress: " + p + " for torrent name: " + a.torrentName());
                        System.out.println(s.stats().totalDownload());
                        break;
                    case TORRENT_FINISHED:
                        System.out.println("Torrent finished");
                        signal.countDown();
                        break;
                }
            }
        });

        s.start();
        TorrentInfo ti = new TorrentInfo(torrentFile);
        s.download(ti, new File( new FileHandling().FolderHandling("Films")));
        try{
            signal.await();
        }catch ( Exception e ){
            throw new RuntimeException(e);
        }
        s.stop();
    }

}
