package com.splendidbits.peacocknews.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.splendidbits.peacocknews.model.*

@Dao
interface ArticlesDao {

    @Transaction
    @Query("select * from batches order by datetime(batches.updated) desc limit 1")
    fun getLatestBatch(): LiveData<Batch?>

    @Transaction
    @Query("select * from items inner join batch_item on batch_item.batchid = :batchId and batch_item.itemId = items.itemId order by datetime(items.published) desc")
    fun getItems(batchId: String?): LiveData<List<Item>>

    @Transaction
    @Query("select * from assets inner join item_asset on item_asset.itemId = :itemId and assets.assetId = item_asset.assetId order by datetime(assets.published) desc")
    fun getItemAssets(itemId: String?): LiveData<List<Asset>>

    @Transaction
    @Query("select * from tags inner join item_tag on item_tag.itemId = :itemId and tags.tagId = item_tag.tagId")
    fun getItemTags(itemId: String?): LiveData<List<Tag>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBatch(batch: Batch?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: Item?, batchItem: BatchItem?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTag(tag: Tag?, itemTag: ItemTag?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAsset(asset: Asset?, itemAsset: ItemAsset?)

    @Transaction
    @Query("delete from batches")
    fun clearBatches()

    @Transaction
    @Query("delete from batch_item")
    fun clearBatchItems()

    @Transaction
    @Query("delete from items")
    fun clearItems()

    @Transaction
    @Query("delete from tags")
    fun clearTags()

    @Transaction
    @Query("delete from item_tag")
    fun clearItemTags()

    @Transaction
    @Query("delete from assets")
    fun clearAssets()

    @Transaction
    @Query("delete from item_asset")
    fun clearItemAssets()
}