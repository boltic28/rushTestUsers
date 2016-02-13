package ru.javarush.rushtest.users.backend;

import org.apache.commons.beanutils.BeanUtils;
import ru.javarush.rushtest.users.backend.model.ModelControl;
import ru.javarush.rushtest.users.backend.model.User;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ControlService {

    private static ModelControl worker;
    private static HashMap<Integer, User> userHashMap;
    SimpleDateFormat forDBWrite = new SimpleDateFormat("YYYY-MM-dd", Locale.ENGLISH);

    public ControlService() {
        worker = new ModelControl();
        userHashMap = worker.getBase();
    }

    public synchronized List<User> findAll(String stringFilter) {
        List arrayList = new ArrayList();
        for (User user : userHashMap.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || user.toString().toLowerCase()
                                .contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(user.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(ControlService.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<User>() {

            @Override
            public int compare(User o1, User o2) {
                return (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }

    public synchronized void delete(User user) {
        userHashMap.remove(user.getId());
        worker.deleteU(user.getId());
    }

    public synchronized void save(User user) {
        if (user.getId() == 0) {
            try {
                user = (User) BeanUtils.cloneBean(user);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            worker.createU(user.getName(), user.getAge(), user.getAdmin(), forDBWrite.format(user.getCreateDate()));
        } else {
            worker.updateU(user.getName(), user.getAge(), user.getAdmin(), user.getId(), forDBWrite.format(user.getCreateDate()));
        }
        userHashMap = worker.getBase();
    }

    public synchronized void generate() {
        worker.generate();
        userHashMap = worker.getBase();
    }

}
