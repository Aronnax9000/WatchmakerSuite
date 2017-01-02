unit OperationMenu;    													{Handle this menu list}

{Unit name:  OperationMenu.p  }
{Function:  Handle this specific menu list.}
{ History: 20/6/91 Original by Prototyper 3.0   }

interface

	uses
		PCommonExhibition, Common_Exhibition,{Common}
		PUtils_Exhibition, Utils_Exhibition, Biomorphs, MacOSAll; {General Utilities}

	{Our menu handler}
	procedure Do_OperationMenu (Doing_Pre: boolean; theItem: integer; var SkipProcessing: boolean);

implementation


{=======================================================}


	{Routine: Do_OperationMenu}
	{Purpose: Handle any menu items in this list specially.}
	{		Get the main handler to ignore this menu item by changing}
	{		SkipProcessing   to be TRUE.}
	{		This routine is called before the main handler does anything}
	{		when Doing_Pre is TRUE, it is called after the main handler}
	{		again with Doing_Pre equal to FALSE.}

	procedure Do_OperationMenu (Doing_Pre: boolean; theItem: integer; var SkipProcessing: boolean);{Handle this menu selection}

	begin     																{Start of procedure}

		SkipProcessing := FALSE;     									{Set to not skip the processing of this menu item}

		case theItem of     												{Handle all commands in this menu list}

			kCoulMenuOperBreed: 
				begin
					if (Doing_Pre) then
						begin
							DoBreed
						end
					else
						begin
						end;
				end;

			kCoulMenuOperNewRandom: 
				begin
					if (Doing_Pre) then
						begin
							EraseRect(BusinessPart);
							DoSaltation(BreedWin);
						end
					else
						begin
						end;
				end;

			otherwise
				begin
				end;

		end;  																{End of item case}

	end;     																	{End of procedure}


{=======================================================}


end.    																		{End of unit}