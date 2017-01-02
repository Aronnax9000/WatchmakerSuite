unit unit_quit;



interface

uses
  unit_preferences;
procedure QuitGracefully(prefs: ArthromorphPreferencesHandle);

implementation

procedure QuitGracefully(prefs: ArthromorphPreferencesHandle);

begin
   DisposeArthromorphPreferences(prefs);
end; {QuitGracefully}

end.

