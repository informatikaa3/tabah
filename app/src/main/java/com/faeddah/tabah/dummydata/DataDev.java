package com.faeddah.tabah.dummydata;

import com.faeddah.tabah.model.Dev;
import com.faeddah.tabah.R;

import java.util.ArrayList;

public class DataDev {
    private static String[] namaDeveloper = {"Anggi Putri P","Denis Aji M","Dimas Putra P","Erni Susilawati","Fathur Waldi L"};
    private static String[] detailDeveloper = {
            "Dia Perempuan",
            "Dia Laki2",
            "Dia Laki2",
            "Dia Perempuan",
            "Dia Laki2"
    };

    private static int[] fotoDeveloper = {
            R.drawable.team_anggi,
            R.drawable.team_denis,
            R.drawable.team_dimas,
            R.drawable.team_erni,
            R.drawable.team_fathur
    };

    public static ArrayList<Dev> getListData(){
        ArrayList<Dev> list = new ArrayList<>();
        for (int i=0; i<namaDeveloper.length;i++){
            Dev developer = new Dev();
            developer.setName(namaDeveloper[i]);
            developer.setDetail(detailDeveloper[i]);
            developer.setFoto(fotoDeveloper[i]);
            list.add(developer);
        }

        return list;
    }
}
