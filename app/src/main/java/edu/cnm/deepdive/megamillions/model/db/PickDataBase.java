package edu.cnm.deepdive.megamillions.model.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.provider.ContactsContract.RawContacts.Data;
import edu.cnm.deepdive.megamillions.model.dao.PickDao;
import edu.cnm.deepdive.megamillions.model.dao.PickNumberDao;
import edu.cnm.deepdive.megamillions.model.db.PickDataBase.Converters;
import edu.cnm.deepdive.megamillions.model.entity.Pick;
import edu.cnm.deepdive.megamillions.model.entity.PickNumber;
import java.util.Date;

@Database(
    entities = {Pick.class, PickNumber.class},
    version = 1,
    exportSchema = true
    )
@TypeConverters(Converters.class)
public abstract class PickDataBase extends RoomDatabase {

  private static final String DB_NAME = "pick_db";

  private static PickDataBase instance = null;

  public abstract PickDao getPickDao();

  public abstract PickNumberDao getPickNumberDao();

  public synchronized static PickDataBase getInstance(Context context) {
    if (instance == null) {
      instance = Room.databaseBuilder(context.getApplicationContext(), PickDataBase.class, DB_NAME)
          .build();
    }
    return instance;
  }

  public synchronized static void forgetInstance() {
    instance = null;
  }

  public static class Converters {
    @TypeConverter
    public static Date dateFromLong(Long time) {
      return (time != null) ? new Date(time) :null;
    }

    @TypeConverter
    public static Long longFromDate(Date date) {
      return (date != null) ? date.getTime() : null;
    }
  }

}
