package fr.nicolabo.myyoutube.model.helper;

/**
 * Interface to notify an item ViewHolder of relevant callbacks from {@link
 * android.support.v7.widget.helper.ItemTouchHelper.Callback}.
 *
 * Created by nicolasboueme on 30/07/2016.
 */
public interface ItemTouchHelperViewHolder {

    /**
     * Called when the {@link ItemTouchHelper} first registers an item as being moved or swiped.
     * Implementations should update the item view to indicate it's active state.
     */
    void onItemSelected();


    /**
     * Called when the {@link ItemTouchHelper} has completed the move or swipe, and the active item
     * state should be cleared.
     */
    void onItemClear();
}
