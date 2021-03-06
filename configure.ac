#                                               -*- Autoconf -*-
# Process this file with autoconf to produce a configure script.

AC_PREREQ(2.61)
AC_INIT([pHash],[0.9.4],[support@phash.org])
AM_INIT_AUTOMAKE($PACKAGE_NAME,$PACKAGE_VERSION)
AC_CONFIG_SRCDIR([src/pHash.cpp])
AC_CONFIG_HEADERS([config.h pHash-config.h])
# Checks for programs.

m4_include([m4/ax_pthread.m4])

AC_PROG_CXX
AC_PROG_CC
AM_PROG_GCJ

if test -z $DISTRO; then
   AC_CHECK_FILE(/etc/gentoo-release, [DISTRO="gentoo"])
   AC_CHECK_FILE(/etc/redhat-release, [DISTRO="redhat"])
   AC_CHECK_FILE(/etc/slackware-version, [DISTRO="slackware"])
   AC_CHECK_FILE(/etc/debian_version, [DISTRO="debian"])
   AC_CHECK_FILE(/etc/SuSErelease, [DISTRO="suse"])
fi

# largefile support is needed for python bindings ( type off_t 64 bits)
AC_SYS_LARGEFILE

AC_ARG_ENABLE(pthread, [AS_HELP_STRING([--disable-pthread],
[pthread support @<:@default=no@:>@])], 
	[PTHREAD="$enableval"],
	[PTHREAD="yes"])

if test "$PTHREAD" = "yes"; then
	AX_PTHREAD([
	LIBS="$PTHREAD_LIBS $LIBS"
	CPPFLAGS="$CFLAGS $PTHREAD_CFLAGS"
	CC="$PTHREAD_CC"
	AC_DEFINE([HAVE_PTHREAD], [1], [configure with pthread support])
	])
fi

AM_CONDITIONAL([HAVE_PTHREAD], [test "$PTHREAD" = "yes"])

AC_ARG_ENABLE(debug, [AS_HELP_STRING([--enable-debug],
[compile with debugging support @<:@default=no@:>@])], 
debug=$enableval, debug=no)
AS_IF([test x"$debug" != x"no"],
	[CXXFLAGS="`echo $CXXFLAGS' ' | sed 's/-O[0-9]//g'`"
	CXXFLAGS="$CXXFLAGS -O0 -g"
	LDFLAGS="$PTHREAD_LIBS $LIBS $CFLAGS $PTHREAD_CFLAGS"
	LDFLAGS="`echo $LDFLAGS | sed 's/-g//g' | sed 's/-O[0-9]//g'`"],
	[CXXFLAGS="`echo $CXXFLAGS' -ffast-math' | sed 's/-g//g' | sed 's/-O[0-9]//g'`"
	CXXFLAGS="$CXXFLAGS -O3"
	CPPFLAGS="`echo $CPPFLAGS | sed 's/-g//g' | sed 's/-O[0-9]//g'`"
	LDFLAGS="$PTHREAD_LIBS $LIBS $CFLAGS $PTHREAD_CFLAGS"
	LDFLAGS="`echo $LDFLAGS | sed 's/-g//g' | sed 's/-O[0-9]//g'`"])

AC_ARG_ENABLE(openmp, [AS_HELP_STRING([--enable-openmp],
[enable OpenMP support in pHash to use multiple cores/CPUs @<:@default=no@:>@])], 
openmp=yes, openmp=no)

AS_IF([test "$openmp" = "yes"],
[
	CPPFLAGS="$CPPFLAGS -Dcimg_use_openmp"
	if test $GCC = "yes"; then
		CXXFLAGS="$CXXFLAGS -fopenmp"
	fi
])

AC_ARG_ENABLE(java, [AS_HELP_STRING([--enable-java],
[compile java (JNI) binding for pHash @<:@default=no@:>@])], 
java=yes, java=no)

AS_IF([test "$java" = "yes"],
[
	AC_CHECK_HEADER([jni.h], [found_jni="y"], [found_jni="n"])

if test x"$found_jni" = x"n"; then
	AC_MSG_CHECKING([whether jni.h is in the current or src directory.])
	
	if [test `ls . src | grep "jni.h"` >/dev/null 2>&1]; then
		AC_MSG_RESULT([yes])
		found_java="y"
	else
		AC_MSG_RESULT([no])
		AC_MSG_CHECKING([if jni.h is in other system directories.])
		jni_header=`locate jni.h|grep include/jni.h|head -n1`
		
		if test x"$jni_header" != "x"; then
			AC_MSG_RESULT([yes])
			jni_header=`dirname $jni_header`
			jni_md_header=$jni_header"/"`uname|tr '[A-Z]' '[a-z]'`
			CPPFLAGS="$CPPFLAGS -I $jni_header -I $jni_md_header"
		else
			AC_MSG_RESULT([no])
		fi
	fi
	
fi

])

AM_CONDITIONAL([WITH_JAVA], test x$java != xno)
AC_PROG_INSTALL
AC_LANG([C++])
AC_PROG_LN_S
m4_defun([_LT_AC_LANG_F77_CONFIG], [:])

AC_PROG_LIBTOOL

AC_SUBST([LIBTOOLS_DEPS])

LDFLAGS="$LDFLAGS -L/usr/local/lib"
CPPFLAGS="$CPPFLAGS -I/usr/local/include"
AC_DEFUN([AC_CHECK_CIMG], [
AC_CHECK_HEADER([CImg.h], [found_cimg="y"], [found_cimg="n"])

if test x"$found_cimg" = x"n"; then
	AC_MSG_CHECKING([whether CImg.h is in the current or src directory.])
	
	if [test `ls . src | grep "CImg.h"` >/dev/null 2>&1]; then
		AC_MSG_RESULT([yes])
		found_cimg="y"
	else
		AC_MSG_RESULT([no])
	fi
	
fi])

AC_DEFUN([AC_CHECK_FFTW3],
[

AC_MSG_CHECKING([whether FFTW3 is present])
AC_CHECK_LIB([fftw3], [fftw_plan_dft_r2c_1d], [], [AC_MSG_ERROR([

*** libfftw3 not found.
You need FFTW3. Get it at <http://fftw.org/>])])
found_fftw="y"])

AC_ARG_ENABLE([image-hash], [AS_HELP_STRING([--enable-image-hash],[include support for perceptual image hashes @<:@default=yes@:>@])],
[image_hash=$enableval], [image_hash=yes])
AS_IF([test x"$image_hash" != x"no"],
	[
	echo
	echo "*** Configuring image hash ***"
	echo
	AC_CHECK_CIMG()
	AC_DEFINE([HAVE_IMAGE_HASH], [1], [configure with image hash])
	])

AM_CONDITIONAL(HAVE_IMAGE_HASH, test x$image_hash != xno)

# Checks for libraries.

AC_CHECK_LIB([jpeg], [jpeg_read_header], [use_jpeg="y"])
AC_CHECK_LIB([m], [sqrt])
AC_CHECK_LIB([png], [png_create_read_struct], [use_png="y"])

AS_IF([test "$found_fftw" = "y"],
[
	CPPFLAGS="$CPPFLAGS -Dcimg_use_fftw3"])
AS_IF([test "$use_jpeg" = "y" -a \( "$image_hash" = "yes" -o "$video_hash" = "yes" \)],
[
	CPPFLAGS="$CPPFLAGS -Dcimg_use_jpeg"
	LDFLAGS="$LDFLAGS -ljpeg"])

AS_IF([test "$use_png" = "y" -a \( "$image_hash" = "yes" -o "$video_hash" = "yes" \)],
[
	CPPFLAGS="$CPPFLAGS -Dcimg_use_png"
	LDFLAGS="$LDFLAGS -lpng"])

CXXFLAGS="$CXXFLAGS --std=c++11"
CFLAGS="$CXXFLAGS"

# Checks for header files.
AC_CHECK_HEADERS([limits.h stdlib.h string.h sys/time.h unistd.h])

# Checks for typedefs, structures, and compiler characteristics.
AC_HEADER_STDBOOL
AC_C_INLINE
AC_TYPE_INT16_T
AC_TYPE_SIZE_T
AC_TYPE_UINT32_T
AC_TYPE_UINT8_T
AC_CHECK_TYPE([uint64_t],[],[AC_MSG_ERROR([

*** Required type uint64_t not found. 
pHash requires 64-bit integers for video and image hashes.])])

# Checks for library functions.
AC_FUNC_ERROR_AT_LINE
AC_CHECK_FUNCS([mremap malloc realloc floor gettimeofday memmove memset pow sqrt strcasecmp strdup strncasecmp])
AC_CONFIG_FILES([Makefile src/Makefile examples/Makefile pHash.pc bindings/Makefile bindings/java/Makefile])
AC_OUTPUT
