ACcatalog
=========

Animal Crossing Item Catalog was designed to make keeping track of which items you have access to easier.
The program comes with two files, the masterIndex.txt which contains a list of known items that I have compiled,
and userIndex.txt, which will be made up of the items that you find.  The program uses masterIndex.txt for reference
for spelling of items, and eventually what items belong to what sets.  Assuming the item you are entering is already
in that file, you don't need to worry about punctuation or capitalization.

The program has little to it right now.  When you first open the program, the main window should have 15 lines of 
dashes and a text field and two buttons at the bottom.  The dashes are where the search results will show up, if
you don't see them, try making the window bigger.  Enter the name of the item you want to check in the field at the
bottom.  If you already have owned and added the item in the past, it should show up in the bevelled box in the center.
If you have not owned or added it yet, there should be a row of dashes inside the bevelled box.  Add the item using
the add button or by pressing the return key.  If you are entering many items at a time, it is likely faster to use
the return key as it highlights the text allowing you to immediatly enter the next item.

Any item that you enter that is not in the masterIndex.txt will automatically be added to that as well.  If you
accidentally add an item you didn't mean to, use the remove button to remove it from your list.  It will remain in
the masterIndex.txt list, however, so you will need to open that in a text editor to remove any misspelled items.

Both files are automatically saved every time an item is added or removed from the list.
