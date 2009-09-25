package com.lehphyro.btracs;

import java.io.*;
import java.util.*;

public interface BTracsFeeder {

	void feed(String title, List<WebSite> sites, Writer writer) throws IOException;

}
