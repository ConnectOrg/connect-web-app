package com.example.chattinfunction;
        import android.graphics.Typeface;
        import android.graphics.drawable.GradientDrawable;
        import android.util.TypedValue;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.LayoutRes;
        import androidx.annotation.Nullable;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.chattinfunction.R;
        import com.example.chattinfunction.commons.ImageLoader;
        import com.example.chattinfunction.commons.ViewHolder;
        import com.example.chattinfunction.commons.models.IDialog;
        import com.example.chattinfunction.commons.models.IMessage;
        import com.example.chattinfunction.utils.DateFormatter;

        import java.lang.reflect.Constructor;
        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.Comparator;
        import java.util.Date;
        import java.util.List;

        import static android.view.View.GONE;
        import static android.view.View.VISIBLE;


//@SuppressWarnings("WeakerAccess")
public class DialogListAdapter<DIALOG extends IDialog>
        extends RecyclerView.Adapter<DialogListAdapter.BaseDialogsViewHolder> {

    protected List<DIALOG> items = new ArrayList<>();
    private int itemLayoutId;
    private Class<? extends BaseDialogsViewHolder> holderClass;
    private ImageLoader imageLoader;
    private com.example.chattinfunction.DialogListAdapter.OnDialogClickListener<DIALOG> onDialogClickListener;
    private com.example.chattinfunction.DialogListAdapter.OnDialogViewClickListener<DIALOG> onDialogViewClickListener;
    private com.example.chattinfunction.DialogListAdapter.OnDialogLongClickListener<DIALOG> onLongItemClickListener;
    private com.example.chattinfunction.DialogListAdapter.OnDialogViewLongClickListener<DIALOG> onDialogViewLongClickListener;
    private DialogsListStyle dialogStyle;
    private DateFormatter.Formatter datesFormatter;

    /**
     * For default list item layout and view holder
     *
     * @param imageLoader image loading method
     */
    public DialogListAdapter(ImageLoader imageLoader) {
        this(R.layout.item_dialog, DialogsViewHolder.class, imageLoader);
    }

    /**
     * For custom list item layout and default view holder
     *
     * @param itemLayoutId custom list item resource id
     * @param imageLoader  image loading method
     */
    public DialogListAdapter(@LayoutRes int itemLayoutId, ImageLoader imageLoader) {
        this(itemLayoutId, DialogsViewHolder.class, imageLoader);
    }

    /**
     * For custom list item layout and custom view holder
     *
     * @param itemLayoutId custom list item resource id
     * @param holderClass  custom view holder class
     * @param imageLoader  image loading method
     */
    public DialogListAdapter(@LayoutRes int itemLayoutId, Class<? extends BaseDialogsViewHolder> holderClass,
                             ImageLoader imageLoader) {
        this.itemLayoutId = itemLayoutId;
        this.holderClass = holderClass;
        this.imageLoader = imageLoader;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(BaseDialogsViewHolder holder, int position) {
        holder.setImageLoader(imageLoader);
        holder.setOnDialogClickListener(onDialogClickListener);
        holder.setOnDialogViewClickListener(onDialogViewClickListener);
        holder.setOnLongItemClickListener(onLongItemClickListener);
        holder.setOnDialogViewLongClickListener(onDialogViewLongClickListener);
        holder.setDatesFormatter(datesFormatter);
        holder.onBind(items.get(position));
    }

    @Override
    public BaseDialogsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayoutId, parent, false);
        //create view holder by reflation
        try {
            Constructor<? extends BaseDialogsViewHolder> constructor = holderClass.getDeclaredConstructor(View.class);
            constructor.setAccessible(true);
            BaseDialogsViewHolder baseDialogsViewHolder = constructor.newInstance(v);
            if (baseDialogsViewHolder instanceof DialogListAdapter.DialogsViewHolder) {
                ((DialogsViewHolder) baseDialogsViewHolder).setDialogStyle(dialogStyle);
            }
            return baseDialogsViewHolder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return size of dialogs list
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * remove item with id
     *
     * @param id dialog i
     */
    public void deleteById(String id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(id)) {
                items.remove(i);
                notifyItemRemoved(i);
            }
        }
    }

    /**
     * Returns {@code true} if, and only if, dialogs count in adapter is non-zero.
     *
     * @return {@code true} if size is 0, otherwise {@code false}
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * clear dialogs list
     */
    public void clear() {
        if (items != null) {
            items.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * Set dialogs list
     *
     * @param items dialogs list
     */
    public void setItems(List<DIALOG> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    /**
     * Add dialogs items
     *
     * @param newItems new dialogs list
     */
    public void addItems(List<DIALOG> newItems) {
        if (newItems != null) {
            if (items == null) {
                items = new ArrayList<>();
            }
            int curSize = items.size();
            items.addAll(newItems);
            notifyItemRangeInserted(curSize, items.size());
        }
    }

    public void setFilter(List<DIALOG> Models){
        items = new ArrayList<>();
        items.addAll(Models);
        notifyDataSetChanged();
    }

    /**
     * Add dialog to the end of dialogs list
     *
     * @param dialog dialog item
     */
    public void addItem(DIALOG dialog) {
        items.add(dialog);
        notifyItemInserted(items.size() - 1);
    }

    /**
     * Add dialog to dialogs list
     *
     * @param dialog   dialog item
     * @param position position in dialogs list
     */
    public void addItem(int position, DIALOG dialog) {
        items.add(position, dialog);
        notifyItemInserted(position);
    }

    /**
     * Move an item
     *
     * @param fromPosition the actual position of the item
     * @param toPosition   the new position of the item
     */
    public void moveItem(int fromPosition, int toPosition) {
        DIALOG dialog = items.remove(fromPosition);
        items.add(toPosition, dialog);
        notifyItemMoved(fromPosition, toPosition);
    }

    /**
     * Update dialog by position in dialogs list
     *
     * @param position position in dialogs list
     * @param item     new dialog item
     */
    public void updateItem(int position, DIALOG item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.set(position, item);
        notifyItemChanged(position);
    }

    /**
     * Update dialog by dialog id
     *
     * @param item new dialog item
     */
    public void updateItemById(DIALOG item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(item.getId())) {
                items.set(i, item);
                notifyItemChanged(i);
                break;
            }
        }
    }

    /**
     * Upsert dialog in dialogs list or add it to then end of dialogs list
     *
     * @param item dialog item
     */
    public void upsertItem(DIALOG item) {
        boolean updated = false;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(item.getId())) {
                items.set(i, item);
                notifyItemChanged(i);
                updated = true;
                break;
            }
        }
        if (!updated) {
            addItem(item);
        }
    }

    /**
     * Find an item by its id
     *
     * @param id the wanted item's id
     * @return the found item, or null
     */
    @Nullable
    public DIALOG getItemById(String id) {
        if (items == null) {
            items = new ArrayList<>();
        }
        for (DIALOG item : items) {
            if (item.getId() == null && id == null) {
                return item;
            } else if (item.getId() != null && item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Update last message in dialog and swap item to top of list.
     *
     * @param dialogId Dialog ID
     * @param message  New message
     * @return false if dialog doesn't exist.
     */
    @SuppressWarnings("unchecked")
    public boolean updateDialogWithMessage(String dialogId, IMessage message) {
        boolean dialogExist = false;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(dialogId)) {
                items.get(i).setLastMessage(message);
                notifyItemChanged(i);
                if (i != 0) {
                    Collections.swap(items, i, 0);
                    notifyItemMoved(i, 0);
                }
                dialogExist = true;
                break;
            }
        }
        return dialogExist;
    }

    /**
     * Sort dialog by last message date
     */
    public void sortByLastMessageDate() {
        Collections.sort(items, (o1, o2) -> {
            if (o1.getLastMessage().getCreatedAt().after(o2.getLastMessage().getCreatedAt())) {
                return -1;
            } else if (o1.getLastMessage().getCreatedAt().before(o2.getLastMessage().getCreatedAt())) {
                return 1;
            } else return 0;
        });
        notifyDataSetChanged();
    }

    /**
     * Sort items with rules of comparator
     *
     * @param comparator Comparator
     */
    public void sort(Comparator<DIALOG> comparator) {
        Collections.sort(items, comparator);
        notifyDataSetChanged();
    }

    /**
     * @return registered image loader
     */
    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    /**
     * Register a callback to be invoked when image need to load.
     *
     * @param imageLoader image loading method
     */
    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    /**
     * @return the item click callback.
     */
    public com.example.chattinfunction.DialogListAdapter.OnDialogClickListener getOnDialogClickListener() {
        return onDialogClickListener;
    }

    /**
     * Register a callback to be invoked when item is clicked.
     *
     * @param onDialogClickListener on click item callback
     */
    public void setOnDialogClickListener(com.example.chattinfunction.DialogListAdapter.OnDialogClickListener<DIALOG> onDialogClickListener) {
        this.onDialogClickListener = onDialogClickListener;
    }

    /**
     * @return the view click callback.
     */
    public com.example.chattinfunction.DialogListAdapter.OnDialogViewClickListener getOnDialogViewClickListener() {
        return onDialogViewClickListener;
    }

    /**
     * Register a callback to be invoked when dialog view is clicked.
     *
     * @param clickListener on click item callback
     */
    public void setOnDialogViewClickListener(com.example.chattinfunction.DialogListAdapter.OnDialogViewClickListener<DIALOG> clickListener) {
        this.onDialogViewClickListener = clickListener;
    }

    /**
     * @return on long click item callback
     */
    public com.example.chattinfunction.DialogListAdapter.OnDialogLongClickListener getOnLongItemClickListener() {
        return onLongItemClickListener;
    }

    /**
     * Register a callback to be invoked when item is long clicked.
     *
     * @param onLongItemClickListener on long click item callback
     */
    public void setOnDialogLongClickListener(com.example.chattinfunction.DialogListAdapter.OnDialogLongClickListener<DIALOG> onLongItemClickListener) {
        this.onLongItemClickListener = onLongItemClickListener;
    }

    /**
     * @return on view long click callback
     */
    public com.example.chattinfunction.DialogListAdapter.OnDialogViewLongClickListener<DIALOG> getOnDialogViewLongClickListener() {
        return onDialogViewLongClickListener;
    }

    /**
     * Register a callback to be invoked when item view is long clicked.
     *
     * @param clickListener on long click item callback
     */
    public void setOnDialogViewLongClickListener(com.example.chattinfunction.DialogListAdapter.OnDialogViewLongClickListener<DIALOG> clickListener) {
        this.onDialogViewLongClickListener = clickListener;
    }

    /**
     * Sets custom {@link DateFormatter.Formatter} for text representation of last message date.
     */
    public void setDatesFormatter(DateFormatter.Formatter datesFormatter) {
        this.datesFormatter = datesFormatter;
    }

    //TODO ability to set style programmatically
    void setStyle(DialogsListStyle dialogStyle) {
        this.dialogStyle = dialogStyle;
    }

    /**
     * @return the position of a dialog in the dialogs list.
     */
    public int getDialogPosition(DIALOG dialog) {
        return this.items.indexOf(dialog);
    }

    /*
     * LISTENERS
     * */
    public interface OnDialogClickListener<DIALOG extends IDialog> {
        void onDialogClick(DIALOG dialog);
    }

    public interface OnDialogViewClickListener<DIALOG extends IDialog> {
        void onDialogViewClick(View view, DIALOG dialog);
    }

    public interface OnDialogLongClickListener<DIALOG extends IDialog> {
        void onDialogLongClick(DIALOG dialog);
    }

    public interface OnDialogViewLongClickListener<DIALOG extends IDialog> {
        void onDialogViewLongClick(View view, DIALOG dialog);
    }

    /*
     * HOLDERS
     * */
    public abstract static class BaseDialogsViewHolder<DIALOG extends IDialog>
            extends ViewHolder<DIALOG> {

        protected ImageLoader imageLoader;
        protected com.example.chattinfunction.DialogListAdapter.OnDialogClickListener<DIALOG> onDialogClickListener;
        protected com.example.chattinfunction.DialogListAdapter.OnDialogLongClickListener<DIALOG> onLongItemClickListener;
        protected com.example.chattinfunction.DialogListAdapter.OnDialogViewClickListener<DIALOG> onDialogViewClickListener;
        protected com.example.chattinfunction.DialogListAdapter.OnDialogViewLongClickListener<DIALOG> onDialogViewLongClickListener;
        protected DateFormatter.Formatter datesFormatter;

        public BaseDialogsViewHolder(View itemView) {
            super(itemView);
        }

        void setImageLoader(ImageLoader imageLoader) {
            this.imageLoader = imageLoader;
        }

        protected void setOnDialogClickListener(com.example.chattinfunction.DialogListAdapter.OnDialogClickListener<DIALOG> onDialogClickListener) {
            this.onDialogClickListener = onDialogClickListener;
        }

        protected void setOnDialogViewClickListener(com.example.chattinfunction.DialogListAdapter.OnDialogViewClickListener<DIALOG> onDialogViewClickListener) {
            this.onDialogViewClickListener = onDialogViewClickListener;
        }

        protected void setOnLongItemClickListener(com.example.chattinfunction.DialogListAdapter.OnDialogLongClickListener<DIALOG> onLongItemClickListener) {
            this.onLongItemClickListener = onLongItemClickListener;
        }

        protected void setOnDialogViewLongClickListener(com.example.chattinfunction.DialogListAdapter.OnDialogViewLongClickListener<DIALOG> onDialogViewLongClickListener) {
            this.onDialogViewLongClickListener = onDialogViewLongClickListener;
        }

        public void setDatesFormatter(DateFormatter.Formatter dateHeadersFormatter) {
            this.datesFormatter = dateHeadersFormatter;
        }
    }

    public static class DialogsViewHolder<DIALOG extends IDialog> extends BaseDialogsViewHolder<DIALOG> {
        protected DialogsListStyle dialogStyle;
        protected ViewGroup container;
        protected ViewGroup root;
        protected TextView tvName;
        protected TextView tvDate;
        protected ImageView ivAvatar;
        protected ImageView ivLastMessageUser;
        protected TextView tvLastMessage;
        protected TextView tvBubble;
        protected ViewGroup dividerContainer;
        protected View divider;

        public DialogsViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.dialogRootLayout);
            container = itemView.findViewById(R.id.dialogContainer);
            tvName = itemView.findViewById(R.id.dialogName);
            tvDate = itemView.findViewById(R.id.dialogDate);
            tvLastMessage = itemView.findViewById(R.id.dialogLastMessage);
            tvBubble = itemView.findViewById(R.id.dialogUnreadBubble);
            ivLastMessageUser = itemView.findViewById(R.id.dialogLastMessageUserAvatar);
            ivAvatar = itemView.findViewById(R.id.dialogAvatar);
            dividerContainer = itemView.findViewById(R.id.dialogDividerContainer);
            divider = itemView.findViewById(R.id.dialogDivider);

        }

        private void applyStyle() {
            if (dialogStyle != null) {
                //Texts
                if (tvName != null) {
                    tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, dialogStyle.getDialogTitleTextSize());
                }

                if (tvLastMessage != null) {
                    tvLastMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, dialogStyle.getDialogMessageTextSize());
                }

                if (tvDate != null) {
                    tvDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, dialogStyle.getDialogDateSize());
                }

                //Divider
                if (divider != null)
                    divider.setBackgroundColor(dialogStyle.getDialogDividerColor());
                if (dividerContainer != null)
                    dividerContainer.setPadding(dialogStyle.getDialogDividerLeftPadding(), 0,
                            dialogStyle.getDialogDividerRightPadding(), 0);
                //Avatar
                if (ivAvatar != null) {
                    ivAvatar.getLayoutParams().width = dialogStyle.getDialogAvatarWidth();
                    ivAvatar.getLayoutParams().height = dialogStyle.getDialogAvatarHeight();
                }

                //Last message user avatar
                if (ivLastMessageUser != null) {
                    ivLastMessageUser.getLayoutParams().width = dialogStyle.getDialogMessageAvatarWidth();
                    ivLastMessageUser.getLayoutParams().height = dialogStyle.getDialogMessageAvatarHeight();
                }

                //Unread bubble
                if (tvBubble != null) {
                    GradientDrawable bgShape = (GradientDrawable) tvBubble.getBackground();
                    bgShape.setColor(dialogStyle.getDialogUnreadBubbleBackgroundColor());
                    tvBubble.setVisibility(dialogStyle.isDialogDividerEnabled() ? VISIBLE : GONE);
                    tvBubble.setTextSize(TypedValue.COMPLEX_UNIT_PX, dialogStyle.getDialogUnreadBubbleTextSize());
                    tvBubble.setTextColor(dialogStyle.getDialogUnreadBubbleTextColor());
                    tvBubble.setTypeface(tvBubble.getTypeface(), dialogStyle.getDialogUnreadBubbleTextStyle());
                }
            }
        }


        private void applyDefaultStyle() {
            if (dialogStyle != null) {
                if (root != null) {
                    root.setBackgroundColor(dialogStyle.getDialogItemBackground());
                }

                if (tvName != null) {
                    tvName.setTextColor(dialogStyle.getDialogTitleTextColor());
                    tvName.setTypeface(Typeface.DEFAULT, dialogStyle.getDialogTitleTextStyle());
                }

                if (tvDate != null) {
                    tvDate.setTextColor(dialogStyle.getDialogDateColor());
                    tvDate.setTypeface(Typeface.DEFAULT, dialogStyle.getDialogDateStyle());
                }

                if (tvLastMessage != null) {
                    tvLastMessage.setTextColor(dialogStyle.getDialogMessageTextColor());
                    tvLastMessage.setTypeface(Typeface.DEFAULT, dialogStyle.getDialogMessageTextStyle());
                }
            }
        }

        private void applyUnreadStyle() {
            if (dialogStyle != null) {
                if (root != null) {
                    root.setBackgroundColor(dialogStyle.getDialogUnreadItemBackground());
                }

                if (tvName != null) {
                    tvName.setTextColor(dialogStyle.getDialogUnreadTitleTextColor());
                    tvName.setTypeface(Typeface.DEFAULT, dialogStyle.getDialogUnreadTitleTextStyle());
                }

                if (tvDate != null) {
                    tvDate.setTextColor(dialogStyle.getDialogUnreadDateColor());
                    tvDate.setTypeface(Typeface.DEFAULT, dialogStyle.getDialogUnreadDateStyle());
                }

                if (tvLastMessage != null) {
                    tvLastMessage.setTextColor(dialogStyle.getDialogUnreadMessageTextColor());
                    tvLastMessage.setTypeface(Typeface.DEFAULT, dialogStyle.getDialogUnreadMessageTextStyle());
                }
            }
        }


        @Override
        public void onBind(final DIALOG dialog) {
            if (dialog.getUnreadCount() > 0) {
                applyUnreadStyle();
            } else {
                applyDefaultStyle();
            }

            //Set Name
            tvName.setText(dialog.getDialogName());

            //Set Date
            String formattedDate = null;

            if (dialog.getLastMessage() != null) {
                Date lastMessageDate = dialog.getLastMessage().getCreatedAt();
                if (datesFormatter != null) formattedDate = datesFormatter.format(lastMessageDate);
                tvDate.setText(formattedDate == null
                        ? getDateString(lastMessageDate)
                        : formattedDate);
            } else {
                tvDate.setText(null);
            }

            //Set Dialog avatar
            if (imageLoader != null) {
                imageLoader.loadImage(ivAvatar, dialog.getDialogPhoto(), null);
            }

            //Set Last message user avatar with check if there is last message
            if (imageLoader != null && dialog.getLastMessage() != null) {
                imageLoader.loadImage(ivLastMessageUser, dialog.getLastMessage().getUser().getAvatar(), null);
            }
            ivLastMessageUser.setVisibility(dialogStyle.isDialogMessageAvatarEnabled()
                    && dialog.getUsers().size() > 1
                    && dialog.getLastMessage() != null ? VISIBLE : GONE);

            //Set Last message text
            if (dialog.getLastMessage() != null) {
                tvLastMessage.setText(dialog.getLastMessage().getText());
            } else {
                tvLastMessage.setText(null);
            }

            //Set Unread message count bubble
            tvBubble.setText(String.valueOf(dialog.getUnreadCount()));
            tvBubble.setVisibility(dialogStyle.isDialogUnreadBubbleEnabled() &&
                    dialog.getUnreadCount() > 0 ? VISIBLE : GONE);

            container.setOnClickListener(view -> {
                if (onDialogClickListener != null) {
                    onDialogClickListener.onDialogClick(dialog);
                }
                if (onDialogViewClickListener != null) {
                    onDialogViewClickListener.onDialogViewClick(view, dialog);
                }
            });


            container.setOnLongClickListener(view -> {
                if (onLongItemClickListener != null) {
                    onLongItemClickListener.onDialogLongClick(dialog);
                }
                if (onDialogViewLongClickListener != null) {
                    onDialogViewLongClickListener.onDialogViewLongClick(view, dialog);
                }
                return onLongItemClickListener != null || onDialogViewLongClickListener != null;
            });
        }

        protected String getDateString(Date date) {
            return DateFormatter.format(date, DateFormatter.Template.TIME);
        }

        protected DialogsListStyle getDialogStyle() {
            return dialogStyle;
        }

        protected void setDialogStyle(DialogsListStyle dialogStyle) {
            this.dialogStyle = dialogStyle;
            applyStyle();
        }
    }
}
