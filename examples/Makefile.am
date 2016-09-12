INCLUDES = -I$(top_srcdir)/src
noinst_PROGRAMS = test_texthash test_texthash2

test_texthash_SOURCES = test_texthash.cpp
test_texthash_LDADD = $(top_srcdir)/src/libpHash.la

test_texthash2_SOURCES = test_texthash2.cpp
test_texthash2_LDADD = $(top_srcdir)/src/libpHash.la

if HAVE_IMAGE_HASH
noinst_PROGRAMS += test_image test_mhimagehash buildmvptreedct addmvptreedct querymvptreedct
buildmvptreedct_SOURCES = buildmvptree_dctimage.cpp
buildmvptreedct_LDADD = $(top_srcdir)/src/libpHash.la

addmvptreedct_SOURCES = add_mvptree_dct.cpp
addmvptreedct_LDADD = $(top_srcdir)/src/libpHash.la

querymvptreedct_SOURCES = query_mvptree_dct.cpp
querymvptreedct_LDADD = $(top_srcdir)/src/libpHash.la

test_image_SOURCES = test_imagephash.cpp
test_image_LDADD = $(top_srcdir)/src/libpHash.la
test_mhimagehash_SOURCES = test_mhimagehash.cpp
test_mhimagehash_LDADD = $(top_srcdir)/src/libpHash.la
endif
