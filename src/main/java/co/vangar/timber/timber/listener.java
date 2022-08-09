package co.vangar.timber.timber;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class listener implements Listener {

    private static Plugin plugin = Bukkit.getPluginManager().getPlugin("Timber");

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Block b = e.getBlock();
        Player p = e.getPlayer();
        if(b.getType().name().toLowerCase().contains("log")){
            if(p.getInventory().getItemInMainHand().getType().name().toLowerCase().contains("axe")){
                if(p.isSneaking() == false){
                    Block seqB = b;
                    List<Block> blocks = new ArrayList<Block>();

                    for(int i = 0; i < 30; i++){
                        if(!blocks.contains(seqB)){
                            blocks.addAll(utils.listLogs(seqB));
                            if(utils.isLog(seqB.getRelative(BlockFace.UP))){
                                seqB = seqB.getRelative(BlockFace.UP);
                            } else {
                                List<Block> listB = new ArrayList<>();
                                listB.add(seqB.getRelative(BlockFace.UP));
                                if(utils.areLogs(listB)){
                                    listB.addAll(utils.addLogs(listB));
                                    for(int g = 0; g < listB.size(); g++){
                                        seqB = listB.get(g);
                                    }
                                } else i=30;
                            }
                        }
                    }

                    if(utils.canBreakAll(p.getInventory().getItemInMainHand(), blocks.size())){
                        new BukkitRunnable(){
                            int count = 0;
                            @Override
                            public void run(){
                                if (count >= blocks.size()){
                                    cancel();
                                    return;
                                } else {
                                    blocks.get(count).breakNaturally();
                                    p.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_STEP, 1f, 1f);

                                    if(p.getGameMode() != GameMode.CREATIVE)
                                        p.getInventory().getItemInMainHand().setItemMeta(utils.durabilityDmg(p.getInventory().getItemInMainHand()));
                                    count++;
                                }
                            }
                        }.runTaskTimer(plugin,3L,2L);
                    }
                }
            }
        }
    }



}
