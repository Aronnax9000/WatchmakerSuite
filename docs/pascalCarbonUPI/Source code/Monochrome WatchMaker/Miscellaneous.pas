unit Miscellaneous;

{******************************************************}
{*												v1.1 Sept 1993														 *}
{*																															 *}
{*		Changed Message procs to use Alerts																 *}
{*		Removed PictMessage (never called), change Message calls to DisplayError	 *}
{*		Added positioning to alerts and dialogs															 *}
{*		Alert now uses STR# for closing, quitting, etc		  										 *}
{*		Added several functions, for EOF and checking system (see below)					 *}
{*     Validated the rect after RestoreOffscreen to prevent drawing twice				 *}
{******************************************************}

interface

uses

	MacOSAll;

function RandInt (Max: Integer): Integer;
function RandInt100: Integer;

function Odd (i: Integer): Boolean;
function TwoToThe (n: integer): Longint;

{*****  procedures added for v1.1 ********}



implementation


{*	Operating System utilities	*}


function TwoToThe (n: integer): Longint;
	VAR
		t: Longint;
	begin
		CASE n OF
			0: 
				t := 1;
			1: 
				t := 2;
			2: 
				t := 4;
			3: 
				t := 8;
			4: 
				t := 16;
			5: 
				t := 32;
			6: 
				t := 64;
			7: 
				t := 128;
			8: 
				t := 256;
			9: 
				t := 512;
			10: 
				t := 1024;
			11: 
				t := 2048;
			12: 
				t := 4096;
			13:
				t := 8192;
			14:
				t := 16384;
			15:
				t := 32768;
			16:
				t := 65536;
		end; {cases}
		TwoToThe := t
	end; {TwoToThe}


function RandInt (Max: Integer): Integer;
	begin
		randint := 1 + (abs(random) MOD max);
	end; {randint}

function RandInt100: Integer;
	begin
		RandInt100 := 1 + (abs(random) MOD 100);
	end; {randint}


function Odd (i: Integer): Boolean;
	begin
		Odd := 2 * (i DIV 2) <> i
	end;

end.