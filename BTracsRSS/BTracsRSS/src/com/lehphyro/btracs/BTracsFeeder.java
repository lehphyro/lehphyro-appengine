package com.lehphyro.btracs;

import java.io.*;
import java.util.*;

public interface BTracsFeeder {

	void feed(List<WebSite> sites, Writer writer) throws IOException;

}
