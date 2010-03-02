package com.lehphyro.gamemcasa.feeder;

import java.io.*;
import java.util.*;

import com.lehphyro.gamemcasa.*;

public interface Feeder {

	void feed(List<Game> games, Writer writer) throws IOException;

}
