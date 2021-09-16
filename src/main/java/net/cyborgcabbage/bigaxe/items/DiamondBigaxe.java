package net.cyborgcabbage.bigaxe.items;

import net.cyborgcabbage.bigaxe.events.TextureListener;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockBase;
import net.minecraft.entity.Living;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.tool.Pickaxe;
import net.minecraft.item.tool.ToolMaterial;
import net.minecraft.level.Level;
import net.minecraft.util.Vec3i;
import net.minecraft.util.maths.TilePos;
import net.minecraft.util.maths.Vec3f;

public class DiamondBigaxe extends Pickaxe {
    public DiamondBigaxe(int id, ToolMaterial material) {
        super(id, material);
    }

    @Environment(EnvType.CLIENT)
    public int getTexturePosition(int damage) {
        return TextureListener.getItemTexture("diamond_bigaxe");
    }

    @Override
    public boolean postMine(ItemInstance arg, int id, int x, int y, int z, Living miner) {
        if(miner instanceof PlayerBase){
            PlayerBase player = (PlayerBase)miner;
            Vec3f eyePos = Vec3f.from(player.x,player.y+player.getStandingEyeHeight()-0.5,player.z);
            System.out.println(x+" "+y+" "+z);
            System.out.println(player.x+" "+(player.y+player.getStandingEyeHeight()-0.5)+" "+player.z);
            Vec3f[] faces = {
                    Vec3f.from(1.0, 0.0, 0.0),
                    Vec3f.from(-1.0, 0.0, 0.0),
                    Vec3f.from(0.0, 1.0, 0.0),
                    Vec3f.from(0.0, -1.0, 0.0),
                    Vec3f.from(0.0, 0.0, 1.0),
                    Vec3f.from(0.0, 0.0, -1.0)
            };
            double value_min = 10.0;
            int index_min = 0;
            Vec3f blockPos = Vec3f.from(x+0.5f,y+0.5f,z+0.5f);
            Vec3f relativeEyePos = Vec3f.from(eyePos.x-blockPos.x,eyePos.y-blockPos.y,eyePos.z-blockPos.z);
            for(int i=0; i<6; i++){
                double faceDistance = faces[i].method_1294(relativeEyePos);
                if (faceDistance < value_min){
                    value_min = faceDistance;
                    index_min = i;
                }
            }
            Vec3f face = faces[index_min];
            Vec3i blockToRemove = new Vec3i(x,y,z);
            for(int i=0;i<2;i++){
                blockToRemove.x -= (int)face.x;
                blockToRemove.y -= (int)face.y;
                blockToRemove.z -= (int)face.z;
                if(miner.level.getTileId(blockToRemove.x,blockToRemove.y,blockToRemove.z) == id) {
                    miner.level.setTile(blockToRemove.x, blockToRemove.y, blockToRemove.z, 0);
                    destroySurroundingOres(miner.level, new TilePos(blockToRemove.x, blockToRemove.y, blockToRemove.z),0, 5);
                }else{
                    break;
                }
            }
        }
        return super.postMine(arg, id, x, y, z, miner);
    }

    private void destroySurroundingOres(Level level, TilePos tilePos, int total, int max) {
        for (int x = tilePos.x - 1; x < tilePos.x + 2; x++) {
            for (int y = tilePos.y - 1; y < tilePos.y + 2; y++) {
                for (int z = tilePos.z - 1; z < tilePos.z + 2; z++) {
                    if (total > max) {
                        return;
                    }
                    int tileId = level.getTileId(x, y, z);
                    if (tileId > 0) {
                        String name = BlockBase.BY_ID[tileId].getName();
                        boolean isOre = false;
                        if (name.endsWith("Ore") || name.endsWith("_ore")) {
                            isOre = true;
                        }else if(name.startsWith("tile.ore") && name.length() > 8){
                            if(Character.isUpperCase(name.charAt(8)) || name.charAt(8) == '_'){
                                isOre = true;
                            }
                        }
                        if (isOre){
                            total++;
                            level.setTile(x, y, z, BlockBase.COBBLESTONE.id);
                            total = destroySurroundingSame(level, new TilePos(x, y, z), tileId, total, max);
                        }
                    }
                }
            }
        }
        return;
    }

    private int destroySurroundingSame(Level level, TilePos tilePos, int tileId, int total, int max) {
        for (int x = tilePos.x - 1; x < tilePos.x + 2; x++) {
            for (int y = tilePos.y - 1; y < tilePos.y + 2; y++) {
                for (int z = tilePos.z - 1; z < tilePos.z + 2; z++) {
                    if(total > max){
                        return total;
                    }
                    if(tileId == level.getTileId(x,y,z)) {
                        total++;
                        level.setTile(x, y, z, BlockBase.COBBLESTONE.id);
                        total = destroySurroundingSame(level, new TilePos(x, y, z), tileId, total, max);
                    }
                }
            }
        }
        return total;
    }
}
